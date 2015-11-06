package org.rapidpm.microservice;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.*;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.reflections.ReflectionUtils;
import org.rapidpm.microservice.optionals.header.ActiveUrlPrinter;
import org.rapidpm.microservice.optionals.header.HeaderScreenPrinter;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.ddi.DdiInjectorFactory;
import org.rapidpm.microservice.servlet.ServletInstanceFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.security.KeyStoreException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.undertow.servlet.Servlets.*;

/**
 * Created by svenruppert on 02.06.15.
 */
public class Main {


  public static final String MYAPP = "/microservice";
  public static final String CONTEXT_PATH_REST = "/rest";

  public static final int DEFAULT_REST_PORT = 7081;
  public static final int DEFAULT_SERVLET_PORT = 7080;
  public static final int DEFAULT_SERVLET_HTTPS_PORT = 7082;
  public static final int DEFAULT_REST_SSL_PORT = 7083;
  public static final String REST_PORT_PROPERTY = "org.rapidpm.microservice.rest.port";
  public static final String REST_HOST_PROPERTY = "org.rapidpm.microservice.rest.host";
  public static final String SERVLET_PORT_PROPERTY = "org.rapidpm.microservice.servlet.port";
  public static final String SERVLET_HOST_PROPERTY = "org.rapidpm.microservice.servlet.host";
  public static final String DEFAULT_HOST = "0.0.0.0";
  private static final String RESTEASY_PORT_PROPERTY = "org.jboss.resteasy.port";
  private static final String RESTEASY_HOST_PROPERTY = "org.jboss.resteasy.host";
  private static final Timer TIMER = new Timer(true);
  private static UndertowJaxrsServer jaxrsServer;
  private static Undertow undertowServer;
  private static Optional<String[]> cliArguments;
  private static SSLContext sslContext;



  private Main() {
  }

  public static void main(String[] args) {
    cliArguments = Optional.ofNullable(args);
    deploy(cliArguments);
  }

  private static LocalDateTime deployStart;

  public static void deploy(Optional<String[]> args) {
    cliArguments = args;
    deployStart = LocalDateTime.now();
    DI.bootstrap(); // per config steuern
    executeStartupActions(args);

    final Undertow.Builder builder = Undertow.builder()
        .setDirectBuffers(true)
        .setServerOption(UndertowOptions.ENABLE_HTTP2, false);


    // deploy servlets
    DeploymentInfo deploymentInfo = createServletDeploymentInfos();
    final boolean anyServlets = !deploymentInfo.getServlets().isEmpty();
    if (anyServlets) {
      try {
        deployServlets(builder, deploymentInfo);
      } catch (ServletException e) {
        e.printStackTrace();
        //TODO logging message
      }
    }

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();
    if (jaxRsActivator.somethingToDeploy()) {
      deployRestRessources(builder, jaxRsActivator);
    } else {
      undertowServer = builder.build();
      undertowServer.start();
    }


    new HeaderScreenPrinter().printOnScreen();
    new ActiveUrlPrinter().printActiveURLs(jaxRsActivator);


    System.out.println("");
    System.out.println("");
    final LocalDateTime stopTime = LocalDateTime.now();
    System.out.println(" ############  Startup finished  = " + stopTime + " ############  ");
    System.out.println(" ############  Startup time [ms] = " + Duration.between(deployStart, stopTime).toMillis() + " ############  ");

  }

  private static void executeStartupActions(final Optional<String[]> args) {
    final Set<Class<? extends MainStartupAction>> classes = DI.getSubTypesOf(MainStartupAction.class);
    createInstances(classes).stream()
        .map(DI::activateDI)
        .forEach((mainStartupAction) -> mainStartupAction.execute(args));
  }

  private static DeploymentInfo createServletDeploymentInfos() {

    final Set<Class<?>> typesAnnotatedWith = DI.getTypesAnnotatedWith(WebServlet.class);

    final List<ServletInfo> servletInfos = typesAnnotatedWith.stream()
        .filter(s -> new ReflectionUtils().checkInterface(s, HttpServlet.class))
        .map(c -> {
          Class<Servlet> servletClass = (Class<Servlet>) c;
          final ServletInfo servletInfo = servlet(c.getSimpleName(), servletClass, new ServletInstanceFactory<>(servletClass));
          if (c.isAnnotationPresent(WebInitParam.class)) {
            final WebInitParam[] annotationsByType = c.getAnnotationsByType(WebInitParam.class);
            for (WebInitParam webInitParam : annotationsByType) {
              final String value = webInitParam.value();
              final String name = webInitParam.name();
              servletInfo.addInitParam(name, value);
            }
          }
          final WebServlet annotation = c.getAnnotation(WebServlet.class);
          final String[] urlPatterns = annotation.urlPatterns();
          for (String urlPattern : urlPatterns) {
            servletInfo.addMapping(urlPattern);
          }
          return servletInfo;
        })
        .collect(Collectors.toList());

    final Set<Class<?>> weblisteners = DI.getTypesAnnotatedWith(WebListener.class);
    final List<ListenerInfo> listenerInfos = weblisteners.stream()
        .map(c -> {
          final ListenerInfo listenerInfo = new ListenerInfo((Class<? extends EventListener>) c);
          return listenerInfo;
        })
        .collect(Collectors.toList());


    return deployment()
        .setClassLoader(Main.class.getClassLoader())
        .setContextPath(MYAPP)
        .setDeploymentName("ROOT" + ".war")
        .setDefaultEncoding("UTF-8")
        .addListeners(listenerInfos)
//        .setResourceManager(new ClassPathResourceManager(Undertow.class.getClassLoader(),""))
//            .setResourceManager(new FileResourceManager(new File("src/main/webapp"), 1024))
        .addServlets(servletInfos);
  }

  private static void deployServlets(final Undertow.Builder builder, final DeploymentInfo deploymentInfo) throws ServletException {
    final ServletContainer servletContainer = defaultContainer();
    DeploymentManager manager = servletContainer.addDeployment(deploymentInfo);
    manager.deploy();
    HttpHandler servletHandler = manager.start();
    PathHandler pathServlet = Handlers
        .path(Handlers.redirect(MYAPP))
        .addPrefixPath(MYAPP, servletHandler);
    final String realServletPort = System.getProperty(SERVLET_PORT_PROPERTY, DEFAULT_SERVLET_PORT + "");
    final String realServletHost = System.getProperty(SERVLET_HOST_PROPERTY, DEFAULT_HOST);

    builder.addHttpListener(Integer.parseInt(realServletPort), realServletHost, pathServlet);
    if(sslContext != null) {
      builder.addHttpsListener(DEFAULT_SERVLET_HTTPS_PORT, realServletHost, sslContext, pathServlet);
    }
  }

  private static void deployRestRessources(final Undertow.Builder builder, final JaxRsActivator jaxRsActivator) {
    final String realRestPort = System.getProperty(REST_PORT_PROPERTY, DEFAULT_REST_PORT + "");
    final String realRestHost = System.getProperty(REST_HOST_PROPERTY, DEFAULT_HOST);

    System.setProperty(RESTEASY_PORT_PROPERTY, realRestPort);
    System.setProperty(RESTEASY_HOST_PROPERTY, realRestHost);

    builder.addHttpListener(Integer.parseInt(realRestPort), realRestHost);
    if(sslContext != null) {
      builder.addHttpsListener(DEFAULT_REST_SSL_PORT, realRestHost, sslContext);
    }
    jaxrsServer = new UndertowJaxrsServer().start(builder);
    final ResteasyDeployment deployment = new ResteasyDeployment();
    deployment.setApplication(jaxRsActivator);
//      deployment.setAsyncJobServiceEnabled(false);
    deployment.setInjectorFactoryClass(DdiInjectorFactory.class.getCanonicalName());
    jaxrsServer.deploy(jaxrsServer.undertowDeployment(deployment)
        .setDeploymentName("Rest")
        .setContextPath(CONTEXT_PATH_REST)
        .setClassLoader(Main.class.getClassLoader()));
  }

  private static <T> List<T> createInstances(final Set<Class<? extends T>> classes) {
    return classes
        .stream()
        .map(c -> {
          try {
            return Optional.of(c.newInstance());
          } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
          }
          return Optional.<T>empty();
        })
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.<T>toList());
  }

  public static void stop(long delayMS) {
    System.out.println("shutdown delay [ms] = " + delayMS);
    TIMER.schedule(new TimerTask() {
      @Override
      public void run() {
        Main.stop();
      }
    }, delayMS);
  }

  public static void stop() {
    executeShutdownActions(cliArguments);

    if (jaxrsServer != null) {
      jaxrsServer.stop();
    } else if (undertowServer != null) {
      undertowServer.stop();
    }
  }

  private static void executeShutdownActions(Optional<String[]> args) {
    final Set<Class<? extends MainShutdownAction>> classes = DI.getSubTypesOf(MainShutdownAction.class);
    createInstances(classes)
        .stream()
        .map(DI::activateDI)
        .forEach((mainShutdownAction) -> mainShutdownAction.execute(args));
  }

  public static void deploy() {
    deploy(Optional.<String[]>empty());
  }

  public static void setSSLContext(SSLContext psslContext) {
    sslContext = psslContext;
  }

  public interface MainStartupAction {
    void execute(Optional<String[]> args);
  }

  public interface MainShutdownAction {
    void execute(Optional<String[]> args);
  }


}

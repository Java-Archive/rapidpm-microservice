package org.rapidpm.microservice;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.*;
import org.apache.commons.cli.CommandLine;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.reflections.ReflectionUtils;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.ddi.DdiInjectorFactory;
import org.rapidpm.microservice.servlet.ServletInstanceFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

  private static final String RESTEASY_PORT_PROPERTY = "org.jboss.resteasy.port";
  private static final String RESTEASY_HOST_PROPERTY = "org.jboss.resteasy.host";

  public static final String REST_PORT_PROPERTY = "org.rapidpm.microservice.rest.port";
  public static final String REST_HOST_PROPERTY = "org.rapidpm.microservice.rest.host";
  public static final String SERVLET_PORT_PROPERTY = "org.rapidpm.microservice.servlet.port";
  public static final String SERVLET_HOST_PROPERTY = "org.rapidpm.microservice.servlet.host";
  public static final String DEFAULT_HOST = "0.0.0.0";

  private static UndertowJaxrsServer jaxrsServer;
  private static Undertow undertowServer;

  private Main() {
  }

  public static void main(String[] args) throws ServletException {

    final CmdLineSingleton cmdLineSingleton = CmdLineSingleton.getInstance();
    cmdLineSingleton.args(args);

    deploy();
  }

  private static final Timer TIMER = new Timer(true);

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
    executeShutdownActions();

    if (jaxrsServer != null) {
      jaxrsServer.stop();
    } else if (undertowServer != null) {
      undertowServer.stop();
    }
  }

  public static void deploy() throws ServletException {
    DI.bootstrap(); // per config steuern
    executeStartupActions();

    initHostAndPorts();

    final Undertow.Builder builder = Undertow.builder()
        .setDirectBuffers(true)
        .setServerOption(UndertowOptions.ENABLE_HTTP2, true);

    // deploy servlets
    DeploymentInfo deploymentInfo = deployServlets();
    final boolean anyServlets = !deploymentInfo.getServlets().isEmpty();
    if (anyServlets) {
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
    }

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();
    final Set<Class<?>> jaxRsActivatorClasses = jaxRsActivator.getClasses();
    final Set<Object> jaxRsActivatorSingletons = jaxRsActivator.getSingletons();
    if (jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty()) {
      undertowServer = builder.build();
      undertowServer.start();
    } else {

      final String realRestPort = System.getProperty(REST_PORT_PROPERTY, DEFAULT_REST_PORT + "");
      final String realRestHost = System.getProperty(REST_HOST_PROPERTY, DEFAULT_HOST);

      System.setProperty(RESTEASY_PORT_PROPERTY, realRestPort);
      System.setProperty(RESTEASY_HOST_PROPERTY, realRestHost);

      builder.addHttpListener(Integer.parseInt(realRestPort), realRestHost);
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

  }

  private static void initHostAndPorts() {
    final CmdLineSingleton cmdLineSingleton = CmdLineSingleton.getInstance();
    final Optional<CommandLine> commandLine = cmdLineSingleton.getCommandLine();
    if (commandLine.isPresent()) {
      final CommandLine cli = commandLine.get();
      if (cli.hasOption(DefaultCmdLineOptions.CMD_SERVLET_PORT)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_SERVLET_PORT).trim();
        System.setProperty(SERVLET_PORT_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_SERVLET_HOST)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_SERVLET_HOST).trim();
        System.setProperty(SERVLET_HOST_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_REST_PORT)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_REST_PORT).trim();
        System.setProperty(REST_PORT_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_REST_HOST)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_REST_HOST).trim();
        System.setProperty(REST_HOST_PROPERTY, optionValue);
      }
    }
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

  private static void executeStartupActions() {
    final Set<Class<? extends MainStartupAction>> classes = DI.getSubTypesOf(MainStartupAction.class);
    createInstances(classes).forEach(MainStartupAction::execute);
  }

  private static void executeShutdownActions() {
    final Set<Class<? extends MainShutdownAction>> classes = DI.getSubTypesOf(MainShutdownAction.class);
    createInstances(classes).forEach(MainShutdownAction::execute);
  }

  private static DeploymentInfo deployServlets() {

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

  public interface MainStartupAction {
    void execute();
  }

  public interface MainShutdownAction {
    void execute();
  }


}

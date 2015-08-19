package org.rapidpm.microservice;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.api.ServletInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.reflections.ReflectionUtils;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.ddi.DdiInjectorFactory;
import org.rapidpm.microservice.servlet.ServletInstanceFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.undertow.servlet.Servlets.*;

/**
 * Created by svenruppert on 02.06.15.
 */
public class Main {


  public static final String MYAPP = "/microservice";
  public static final String CONTEXT_PATH_REST = "/rest";

  public static final String RESTEASY_PORT = "org.jboss.resteasy.port";
  public static final String RESTEASY_HOST = "org.jboss.resteasy.host";

  public static final int PORT_REST = 7081;
  public static final int PORT_SERVLET = 7080;


  private static UndertowJaxrsServer jaxrsServer;
  private static Undertow undertowServer;

  private Main() {
  }

  public static void main(String[] args) throws ServletException {
    deploy();
  }

  public static void stop() {
    if (jaxrsServer != null) {
      jaxrsServer.stop();
    } else if (undertowServer != null) {
      undertowServer.stop();
    }
  }

  public static void deploy() throws ServletException {

    DI.bootstrap(); // per config steuern

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

      builder.addHttpListener(PORT_SERVLET, "0.0.0.0", pathServlet);
    }

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();

    final Set<Class<?>> jaxRsActivatorClasses = jaxRsActivator.getClasses();
    final Set<Object> jaxRsActivatorSingletons = jaxRsActivator.getSingletons();
    if (jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty()) {
      undertowServer = builder.build();
      undertowServer.start();
    } else {
      builder.addHttpListener(PORT_REST, "0.0.0.0"); //REST ohne handler
      System.setProperty(RESTEASY_PORT, PORT_REST + ""); //TODO
      jaxrsServer = new UndertowJaxrsServer().start(builder);
      final ResteasyDeployment deployment = new ResteasyDeployment();
      deployment.setApplication(jaxRsActivator);
      deployment.setAsyncJobServiceEnabled(false);
      deployment.setInjectorFactoryClass(DdiInjectorFactory.class.getCanonicalName());
      jaxrsServer.deploy(jaxrsServer.undertowDeployment(deployment)
          .setDeploymentName("Rest")
          .setContextPath(CONTEXT_PATH_REST)
          .setClassLoader(Main.class.getClassLoader()));
    }

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
    return deployment()
        .setClassLoader(Main.class.getClassLoader())
        .setContextPath(MYAPP)
        .setDeploymentName("ROOT" + ".war")
        .setDefaultEncoding("UTF-8")
//        .setResourceManager(new ClassPathResourceManager(Undertow.class.getClassLoader(),""))
//            .setResourceManager(new FileResourceManager(new File("src/main/webapp"), 1024))
        .addServlets(servletInfos);
  }
}

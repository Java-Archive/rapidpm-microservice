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
import org.rapidpm.ddi.ReflectionsSingleton;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.ddi.DdiInjectorFactory;
import org.rapidpm.microservice.servlet.ServletInstanceFactory;

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


  public static final String MYAPP = "microservice";
  public static final String CONTEXT_PATH_REST = "/rest";

  public static final String RESTEASY_PORT = "org.jboss.resteasy.port";
  public static final String RESTEASY_HOST = "org.jboss.resteasy.host";

  public static final int PORT_REST = 7081;
  public static final int PORT_SERVLET = 7080;


  private static UndertowJaxrsServer server;


  private Main() {
  }

  public static void main(String[] args) throws ServletException {
    deploy();
  }

  public static void stop() {
    server.stop();
  }

  public static void deploy() throws ServletException {


    final Undertow.Builder builder = Undertow.builder()
        .setDirectBuffers(true)
//        .setIoThreads(10)
        .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
        .setServerOption(UndertowOptions.ENABLE_SPDY, true);


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

      builder.addHttpListener(PORT_SERVLET, "0.0.0.0", pathServlet); //f Servlet
    }

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();

    final Set<Class<?>> jaxRsActivatorClasses = jaxRsActivator.getClasses();
    final Set<Object> jaxRsActivatorSingletons = jaxRsActivator.getSingletons();
    if (jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty()) {
      //TODO kein REST gestartet
    } else {

      builder.addHttpListener(PORT_REST, "0.0.0.0"); //REST ohne handler
      System.setProperty(RESTEASY_PORT, PORT_REST + ""); //TODO
      server = new UndertowJaxrsServer().start(builder);
      final ResteasyDeployment deployment = new ResteasyDeployment();
      deployment.setApplication(jaxRsActivator);
      deployment.setAsyncJobServiceEnabled(false);
      deployment.setInjectorFactoryClass(DdiInjectorFactory.class.getCanonicalName());
      server.deploy(server.undertowDeployment(deployment)
          .setDeploymentName("Rest")
          .setContextPath(CONTEXT_PATH_REST)
          .setClassLoader(Main.class.getClassLoader()));
    }

  }

  private static DeploymentInfo deployServlets() {

    final Set<Class<? extends HttpServlet>> subTypesOf = ReflectionsSingleton.REFLECTIONS.getSubTypesOf(HttpServlet.class);
    final List<ServletInfo> servletInfos = subTypesOf.stream()
        .map(c -> {
          final ServletInfo servletInfo = servlet(c.getSimpleName(), c, new ServletInstanceFactory<>(c));
          if (c.isAnnotationPresent(WebInitParam.class)) {
            final WebInitParam[] annotationsByType = c.getAnnotationsByType(WebInitParam.class);
            for (WebInitParam webInitParam : annotationsByType) {
              final String value = webInitParam.value();
              final String name = webInitParam.name();
              servletInfo.addInitParam(name, value);
            }
          }

          if (c.isAnnotationPresent(WebServlet.class)) {
            final WebServlet annotation = c.getAnnotation(WebServlet.class);
            final String[] urlPatterns = annotation.urlPatterns();
            for (String urlPattern : urlPatterns) {
              servletInfo.addMapping(urlPattern);
            }
          }
          return servletInfo;
        })
        .collect(Collectors.toList());

    return deployment()
        .setClassLoader(Main.class.getClassLoader())
        .setContextPath(MYAPP)
        .setDeploymentName("ROOT.war")
        .setDefaultEncoding("UTF-8")
        .addServlets(servletInfos);
  }
}

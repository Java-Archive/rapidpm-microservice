package org.rapidpm.microservice;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.MetricsHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.MetricsCollector;
import io.undertow.servlet.api.ServletInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.servlet.ServletInstanceFactory;
import org.reflections.Reflections;

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
  private static UndertowJaxrsServer server;
  private static final Reflections REFLECTIONS = new Reflections("");

  private Main() {
  }

  public static void main(String[] args) throws ServletException {
    deploy();
  }

  public static void stop() {
    server.stop();
  }

  public static void deploy() throws ServletException {

    DeploymentInfo servletBuilder = deployMessageServlet();
    DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
    manager.deploy();

    HttpHandler servletHandler = manager.start();
    PathHandler pathServlet = Handlers
        .path(Handlers.redirect(MYAPP))
        .addPrefixPath(MYAPP, servletHandler);

    final Undertow.Builder builder = Undertow.builder()
        .setDirectBuffers(true)
//        .setIoThreads(20)
        .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
        .setServerOption(UndertowOptions.ENABLE_SPDY, true)
        .addHttpListener(7081, "0.0.0.0") //REST ohne handler
        .addHttpListener(7080, "0.0.0.0", pathServlet); //f Servlet
//          .setHandler(pathServlet);

    System.setProperty("org.jboss.resteasy.port", "7081"); //TODO
    server = new UndertowJaxrsServer().start(builder);

    final ResteasyDeployment deployment = new ResteasyDeployment();
//    deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();
    jaxRsActivator.setReflections(REFLECTIONS);

    deployment.setApplication(jaxRsActivator);
    deployment.setAsyncJobServiceEnabled(true);
//    deployment.setProviderFactory();
//    deployment.setInjectorFactoryClass();
    server.deploy(server.undertowDeployment(deployment)
        .setDeploymentName("Rest")
        .setContextPath("/rest")
//        .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class))
        .setClassLoader(Main.class.getClassLoader()));
  }

  private static DeploymentInfo deployMessageServlet() {

    final Set<Class<? extends HttpServlet>> subTypesOf = REFLECTIONS.getSubTypesOf(HttpServlet.class);
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
            .setMetricsCollector(new MetricsCollector() {
              @Override
              public void registerMetric(final String servletName, final MetricsHandler handler) {

              }
            })
//          .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class))
        .addServlets(//virtualProxy for Servlet - activate CDI
            servletInfos
        );
  }
}

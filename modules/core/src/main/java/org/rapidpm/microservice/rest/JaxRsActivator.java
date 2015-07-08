package org.rapidpm.microservice.rest;

import org.reflections.Reflections;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

/**
 * Created by sven on 27.05.15.
 */
@ApplicationPath("/base")
public class JaxRsActivator extends Application {
  private Reflections reflections;

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Path.class);
    return annotated;
  }

  /**
   * Hier kann man dann die Proxies holen ?
   * @return
   */
  public Set<Object> getSingletons() {
    return Collections.emptySet();
  }

  public void setReflections(final Reflections reflections) {
    this.reflections = reflections;
  }
}
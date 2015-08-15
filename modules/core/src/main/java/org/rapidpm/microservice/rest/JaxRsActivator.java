package org.rapidpm.microservice.rest;


import org.rapidpm.ddi.reflections.ReflectionsModel;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sven on 27.05.15.
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> typesAnnotatedWith = ReflectionsModel.REFLECTIONS.getTypesAnnotatedWith(Path.class);
    Set<Class<?>> result = new HashSet<>();
    for (Class<?> aClass : typesAnnotatedWith) {
      if (! aClass.getCanonicalName().contains("org.jboss")) {
        result.add(aClass);
      }
    }
    return result;
  }

  /**
   * Hier kann man dann die Proxies holen ?
   *
   * @return
   */
  public Set<Object> getSingletons() {
    //TODO DDI aktivieren
    return Collections.emptySet();
  }

}

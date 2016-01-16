package org.rapidpm.microservice.rest;


import org.rapidpm.ddi.DI;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Sven Ruppert on 27.05.15.
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

  public boolean somethingToDeploy() {
    final Set<Class<?>> jaxRsActivatorClasses = getClasses();
    final Set<Object> jaxRsActivatorSingletons = getSingletons();
    return !(jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty());
  }

  @Override
  public Set<Class<?>> getClasses() {
    return DI.getTypesAnnotatedWith(Path.class, true)
        .stream()
        .filter(aClass -> !aClass.getCanonicalName().contains("org.jboss"))
        .collect(toSet());
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

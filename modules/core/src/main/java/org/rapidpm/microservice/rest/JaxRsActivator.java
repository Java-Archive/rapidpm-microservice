/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.rest;


import static java.util.stream.Collectors.toSet;
import static org.rapidpm.ddi.DI.getTypesAnnotatedWith;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import org.rapidpm.ddi.DI;


@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

  private static final Predicate<? super Class<?>> JBOSS_PACKAGE_FILTER = aClass -> !aClass.getCanonicalName().contains("org.jboss");

  public boolean somethingToDeploy() {
    final Set<Class<?>> jaxRsActivatorClasses = getClasses();
    final Set<Object> jaxRsActivatorSingletons = getSingletons();
    return ! (jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty());
  }

  @Override
  public Set<Class<?>> getClasses() {
    Stream<Class<?>> pathStream = getTypesAnnotatedWith(Path.class, true)
        .stream()
        .filter(JBOSS_PACKAGE_FILTER)
        .filter(aClass -> !aClass.isInterface());

    Stream<Class<?>> providerStream = DI.getTypesAnnotatedWith(Provider.class, true).stream();

    return Stream.concat(pathStream, providerStream).collect(Collectors.toSet());
  }

  public Set<Class<?>> getPathResources() {
    return getTypesAnnotatedWith(Path.class, true).stream()
        .filter(JBOSS_PACKAGE_FILTER)
        .collect(Collectors.toSet());
  }

  /**
   * Hier kann man dann die Proxies holen ?
   *
   * @return
   */
  @Override
  public Set<Object> getSingletons() {
    return Collections.emptySet();
  }

  public Set<Class<?>> getInterfacesWithPathAnnotation() {
    return getTypesAnnotatedWith(Path.class , true)
        .stream()
        .filter(JBOSS_PACKAGE_FILTER)
        .filter(Class::isInterface)
        .collect(toSet());
  }

}

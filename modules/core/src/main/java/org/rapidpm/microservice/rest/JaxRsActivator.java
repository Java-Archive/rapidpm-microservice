/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.rest;


import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import org.rapidpm.ddi.DI;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

  public boolean somethingToDeploy() {
    final Set<Class<?>> jaxRsActivatorClasses = getClasses();
    final Set<Object> jaxRsActivatorSingletons = getSingletons();
    return !(jaxRsActivatorClasses.isEmpty() && jaxRsActivatorSingletons.isEmpty());
  }

  @Override
  public Set<Class<?>> getClasses() {
    Stream<Class<?>> pathStream = DI.getTypesAnnotatedWith(Path.class, true)
        .stream()
        .filter(aClass -> !aClass.getCanonicalName().contains("org.jboss"));

    Stream<Class<?>> providerStream = DI.getTypesAnnotatedWith(Provider.class, true).stream();

    return Stream.concat(pathStream, providerStream).collect(Collectors.toSet());
  }

  /**
   * Hier kann man dann die Proxies holen ?
   *
   * @return
   */
  @Override
  public Set<Object> getSingletons() {
    //TODO DDI aktivieren
    return Collections.emptySet();
  }
}

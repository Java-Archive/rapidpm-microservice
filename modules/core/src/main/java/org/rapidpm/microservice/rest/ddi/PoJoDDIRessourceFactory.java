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
package org.rapidpm.microservice.rest.ddi;

import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;
import org.jboss.resteasy.spi.ConstructorInjector;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.PropertyInjector;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.metadata.ResourceBuilder;
import org.jboss.resteasy.spi.metadata.ResourceClass;
import org.jboss.resteasy.spi.metadata.ResourceConstructor;
import org.rapidpm.ddi.DI;

/**
 *
 */
public class PoJoDDIRessourceFactory implements ResourceFactory {
  private final   Class<?>            scannableClass;
    private final ResourceClass       resourceClass;
  private         ConstructorInjector constructorInjector;
  private         PropertyInjector    propertyInjector;

  public PoJoDDIRessourceFactory(Class<?> scannableClass) {
    this.scannableClass = scannableClass;
    this.resourceClass = ResourceBuilder.rootResourceFromAnnotations(scannableClass);
  }

//  public PoJoDDIRessourceFactory(ResourceClass resourceClass) {
//    this.scannableClass = resourceClass.getClazz();
//    this.resourceClass = resourceClass;
//  }

  public void registered(ResteasyProviderFactory factory) {
    System.out.println("registered - factory = " + factory);
    ResourceConstructor constructor = this.resourceClass.getConstructor();
    if (constructor == null) {
      final Class<?> clazz = this.resourceClass.getClazz();
      final Class<?> aClass = DI.getSubTypesWithoutInterfacesAndGeneratedOf(clazz).stream().findFirst().get();
      constructor = ResourceBuilder.constructor(aClass);
    }
//
    if (constructor == null) {
      throw new RuntimeException(Messages.MESSAGES.unableToFindPublicConstructorForClass(this.scannableClass.getName()));
    } else {
      this.constructorInjector = factory.getInjectorFactory().createConstructor(constructor , factory);
      this.propertyInjector = factory.getInjectorFactory().createPropertyInjector(this.resourceClass , factory);
    }
  }

  public Object createResource(HttpRequest request , HttpResponse response , ResteasyProviderFactory factory) {
//    Object obj = this.constructorInjector.construct(request , response);
//    this.propertyInjector.inject(request , response , obj);
//    return obj;
    return DI.activateDI(scannableClass);
  }

  public void unregistered() {
  }

  public Class<?> getScannableClass() {
    return this.scannableClass;
  }

  public void requestFinished(HttpRequest request , HttpResponse response , Object resource) {
  }
}
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

import org.jboss.resteasy.core.InjectorFactoryImpl;
import org.jboss.resteasy.core.ValueInjector;
import org.jboss.resteasy.spi.*;
import org.jboss.resteasy.spi.metadata.Parameter;
import org.jboss.resteasy.spi.metadata.ResourceClass;
import org.jboss.resteasy.spi.metadata.ResourceConstructor;
import org.jboss.resteasy.spi.metadata.ResourceLocator;
import org.rapidpm.microservice.rest.JaxRsActivator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * @author Sven Ruppert
 */
public class DdiInjectorFactory implements InjectorFactory {

//  private DDI manager; //TODO instance create

  private InjectorFactory delegate = new InjectorFactoryImpl();

  @Override
  public ConstructorInjector createConstructor(Constructor constructor, ResteasyProviderFactory factory) {
    Class<?> clazz = constructor.getDeclaringClass();

    ConstructorInjector injector = ddiConstructor(clazz);
    if (injector != null) return injector;

    return delegate.createConstructor(constructor, factory);
  }

  @Override
  public PropertyInjector createPropertyInjector(Class resourceClass, ResteasyProviderFactory factory) {
    return new DdiPropertyInjector(
        delegate.createPropertyInjector(resourceClass, factory),
        resourceClass);
  }

  @Override
  public ValueInjector createParameterExtractor(Class injectTargetClass, AccessibleObject injectTarget, Class type, Type genericType, Annotation[] annotations, ResteasyProviderFactory factory) {
    return delegate.createParameterExtractor(
        injectTargetClass,
        injectTarget, type, genericType, annotations, factory);
  }

  public ValueInjector createParameterExtractor(Class injectTargetClass, AccessibleObject injectTarget, Class type,
                                                Type genericType, Annotation[] annotations, boolean useDefault, ResteasyProviderFactory factory) {
    return delegate.createParameterExtractor(injectTargetClass, injectTarget, type, genericType, annotations, useDefault, factory);
  }

  @Override
  public ValueInjector createParameterExtractor(Parameter parameter, ResteasyProviderFactory providerFactory) {
    return delegate.createParameterExtractor(parameter, providerFactory);
  }

  @Override
  public MethodInjector createMethodInjector(ResourceLocator method, ResteasyProviderFactory factory) {
    return delegate.createMethodInjector(method, factory);
  }

  @Override
  public PropertyInjector createPropertyInjector(ResourceClass resourceClass, ResteasyProviderFactory providerFactory) {
    final PropertyInjector propertyInjector = delegate.createPropertyInjector(resourceClass, providerFactory);
    return new DdiPropertyInjector(propertyInjector, resourceClass.getClazz());
  }

  @Override
  public ConstructorInjector createConstructor(ResourceConstructor constructor, ResteasyProviderFactory providerFactory) {
    Class<?> clazz = constructor.getConstructor().getDeclaringClass();

    ConstructorInjector injector = ddiConstructor(clazz);
    if (injector != null) return injector;

    return delegate.createConstructor(constructor, providerFactory);
  }

  protected ConstructorInjector ddiConstructor(Class<?> clazz) {
    if (new JaxRsActivator().getClasses().contains(clazz)) {
      return new DdiConstructorInjector(clazz);
    } else return null;
  }


}

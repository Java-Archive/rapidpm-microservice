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
package org.rapidpm.microservice.rest.ddi;

import org.jboss.resteasy.spi.*;
import org.rapidpm.ddi.DI;

import javax.ws.rs.WebApplicationException;
import java.lang.reflect.Type;

/**
 * This ConstructorInjector implementation uses DDI to obtain
 * a contextual instance of a bean.
 *
 * @author Sven Ruppert
 */
public class DdiConstructorInjector implements ConstructorInjector {
  public static final Object[] OBJECTS = new Object[0];

  private Type type;

  public DdiConstructorInjector(Type type) {
    this.type = type;
  }

  public Object construct() {

    //TODO create instance wit injected properties ?
    try {
      final Object t = ((Class) type).newInstance();
      DI.getInstance().activateDI(t);
      return t;
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Object construct(HttpRequest request, HttpResponse response) throws Failure, WebApplicationException, ApplicationException {
    return construct();
  }

  public Object[] injectableArguments() {
    return OBJECTS;
  }

  public Object[] injectableArguments(HttpRequest request, HttpResponse response) throws Failure {
    return injectableArguments();
  }
}

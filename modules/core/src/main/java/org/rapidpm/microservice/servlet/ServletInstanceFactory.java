package org.rapidpm.microservice.servlet;

import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import org.rapidpm.ddi.DI;
import org.rapidpm.proxybuilder.ProxyBuilder;
import org.rapidpm.proxybuilder.type.dymamic.virtual.CreationStrategy;

import javax.servlet.Servlet;


/**
 * Created by svenruppert on 31.05.15.
 */
public class ServletInstanceFactory<T extends Servlet> implements InstanceFactory<Servlet> {


  private final Class<T> servletClass;

  public ServletInstanceFactory(Class<T> servletClass) {
    this.servletClass = servletClass;
  }

  @Override
  public InstanceHandle<Servlet> createInstance() throws InstantiationException {
    return new InstanceHandle<Servlet>() {
      @Override
      public Servlet getInstance() {
        try {
          final T t = servletClass.newInstance();
          DI.activateDI(t);
          return ProxyBuilder
              .newDynamicProxyBuilder(Servlet.class, t.getClass(), CreationStrategy.NO_DUPLICATES)
              .addMetrics()
              .build();
//          return t;
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
        }
        return null;
      }

      @Override
      public void release() {
        //release ???
      }
    };
  }
}

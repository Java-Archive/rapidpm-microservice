package org.rapidpm.microservice.servlet;

import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import org.rapidpm.ddi.DI;

import javax.servlet.http.HttpServlet;


/**
 * Created by svenruppert on 31.05.15.
 */
public class ServletInstanceFactory<T extends HttpServlet> implements InstanceFactory<HttpServlet> {


  private final Class<T> servletClass;

  public ServletInstanceFactory(Class<T> servletClass) {
    this.servletClass = servletClass;
  }

  @Override
  public InstanceHandle<HttpServlet> createInstance() throws InstantiationException {
    return new InstanceHandle<HttpServlet>() {
      @Override
      public HttpServlet getInstance() {
        try {
          final T t = servletClass.newInstance();
          DI.activateDI(t);
//          return ProxyBuilder
//              .newDynamicVirtualProxyBuilder(Servlet.class, t.getClass(), CreationStrategy.NO_DUPLICATES)
//              .addMetrics()
//              .build();
          return t;
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

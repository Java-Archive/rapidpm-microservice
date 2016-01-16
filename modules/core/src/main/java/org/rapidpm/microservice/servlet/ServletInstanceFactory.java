package org.rapidpm.microservice.servlet;

import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import org.rapidpm.ddi.DI;

import javax.servlet.http.HttpServlet;


/**
 * Created by Sven Ruppert on 31.05.15.
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
        return DI.activateDI(servletClass);
      }

      @Override
      public void release() {
        //release ???
      }
    };
  }
}

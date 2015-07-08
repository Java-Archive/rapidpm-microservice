package org.rapidpm.microservice.servlet;

import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;

//import javax.enterprise.inject.Instance;
//import javax.enterprise.inject.spi.CDI;
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
        System.out.println("getInstance..." + servletClass );
//        final CDI<Object> current = CDI.current();
//        final Instance<T> select = current.select(servletClass);
//        return select.get(); //wieviele Instanzen erzeugen...?
        try {
          return servletClass.newInstance();
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

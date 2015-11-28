package org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

import javax.servlet.ServletException;

/**
 * Created by svenruppert on 15.08.15.
 */
public class DDIVaadinServlet extends VaadinServlet {


  //add Metrics here

  @Override
  protected void servletInitialized() throws ServletException {
    super.servletInitialized();

  }

  @Override
  protected VaadinServletService createServletService(final DeploymentConfiguration deploymentConfiguration) throws ServiceException {
    final DDIVaadinServletService service = new DDIVaadinServletService(this, deploymentConfiguration);
    service.init();
    return service;
  }

}

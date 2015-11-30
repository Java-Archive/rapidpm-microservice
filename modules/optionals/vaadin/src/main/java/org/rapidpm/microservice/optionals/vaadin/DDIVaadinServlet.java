package org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

import javax.servlet.ServletException;
import java.util.List;

/**
 * Created by svenruppert on 15.08.15.
 */
public abstract class DDIVaadinServlet extends VaadinServlet {

  /**
   * return a list of pkg names that are available for Injection
   * @return
   */
  public abstract List<String> topLevelPackagesToActivated();

  //add Metrics here

  @Override
  protected void servletInitialized() throws ServletException {
    super.servletInitialized();

  }

  @Override
  protected VaadinServletService createServletService(final DeploymentConfiguration deploymentConfiguration) throws ServiceException {
    final DDIVaadinServletService service = new DDIVaadinServletService(this, deploymentConfiguration, topLevelPackagesToActivated());
    service.init();
    return service;
  }

}

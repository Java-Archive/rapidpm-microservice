package org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.rapidpm.ddi.DI;
import org.reflections.util.ClasspathHelper;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by svenruppert on 15.08.15.
 */
public class DDIVaadinServletService extends VaadinServletService {

  public static final String PKG_PREFIX = "org.rapidpm"; // must come from external config

  public DDIVaadinServletService(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration) throws ServiceException {
    super(servlet, deploymentConfiguration);

    if (DI.isPkgPrefixActivated(PKG_PREFIX)) {
      //nothing....
      //System.out.println("DI.getPkgPrefixActivatedTimestamp() = " + DI.getPkgPrefixActivatedTimestamp(PKG_PREFIX));
    } else {
      //System.out.println("DDIVaadinServletService-DI.activatePackages-LocalDateTime.now() = " + LocalDateTime.now());
      final ServletContext currentServletContext = servlet.getServletContext();
      final Collection<URL> urlsWebInfLib = ClasspathHelper.forWebInfLib(currentServletContext);

      final List<URL> urlList = urlsWebInfLib.stream()
          .filter(u -> u.toString().contains("rapidpm-"))  //reduce the jars to scann
          .collect(Collectors.toList());
      DI.activatePackages(PKG_PREFIX, urlList); //reduce the classes to scann
      System.out.println("DDIVaadinServletService-DI.activatePackages-LocalDateTime.now() = " + LocalDateTime.now());
      //inject
    }


    addSessionInitListener(event -> event.getSession().addUIProvider(new DefaultUIProvider() {
      @Override
      public UI createInstance(final UICreateEvent event) {
        final UI instance = super.createInstance(event);
        //metrics - System.out.println("DDIVaadinServletService.createInstance-instance = " + instance);
        return DI.activateDI(instance);
      }
    }));

    addSessionDestroyListener(event -> {
      //System.out.println("addSessionDestroyListener-event = " + event);
//      VaadinSessionDestroyEvent sessionDestroyEvent = new VaadinSessionDestroyEvent(CDIUtil.getSessionId(event.getSession()));
//      getBeanManager().fireEvent(sessionDestroyEvent);
    });
  }


  @Override
  public void handleRequest(VaadinRequest request, VaadinResponse response) throws ServiceException {
    super.handleRequest(request, response);
    //System.out.println("handleRequest-request = " + request.getContextPath());
  }

  @PostConstruct
  public void initialize() {
    //System.out.println("DDIVaadinServletService = initialize " + LocalDateTime.now());
  }
}

package org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.rapidpm.ddi.DI;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * Created by Sven Ruppert on 15.08.15.
 */
public class DDIVaadinServletService extends VaadinServletService {


  public DDIVaadinServletService(VaadinServlet servlet,
                                 DeploymentConfiguration deploymentConfiguration,
                                 List<String> topLevelPackagesToActivated)
      throws ServiceException {

    super(servlet, deploymentConfiguration);

    topLevelPackagesToActivated
        .stream()
        .filter(pkg -> !DI.isPkgPrefixActivated(pkg))
        .forEach(DI::activatePackages);

    addSessionInitListener(event -> event.getSession().addUIProvider(new DefaultUIProvider() {
      @Override
      public UI createInstance(final UICreateEvent event) {
        final UI instance = super.createInstance(event);
        return DI.activateDI(instance);
      }
    }));

    addSessionDestroyListener(event -> {
//      VaadinSessionDestroyEvent sessionDestroyEvent = new VaadinSessionDestroyEvent(CDIUtil.getSessionId(event.getSession()));
//      getBeanManager().fireEvent(sessionDestroyEvent);
    });
  }


  @Override
  public void handleRequest(VaadinRequest request, VaadinResponse response) throws ServiceException {
    super.handleRequest(request, response);
  }

  @PostConstruct
  public void initialize() {
  }
}

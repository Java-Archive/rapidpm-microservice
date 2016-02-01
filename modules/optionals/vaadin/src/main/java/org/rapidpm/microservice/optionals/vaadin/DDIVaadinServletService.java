/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.rapidpm.ddi.DI;

import javax.annotation.PostConstruct;
import java.util.List;


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

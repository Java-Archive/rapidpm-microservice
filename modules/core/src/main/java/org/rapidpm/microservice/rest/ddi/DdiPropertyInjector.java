/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.rest.ddi;

import org.jboss.resteasy.spi.*;
import org.rapidpm.microservice.rest.JaxRsActivator;

import javax.ws.rs.WebApplicationException;

/**
 * no property injection for DDI managed beans
 *
 * @author Sven Ruppert
 */
public class DdiPropertyInjector implements PropertyInjector {

  private PropertyInjector delegate;
  private Class<?> clazz;
  private boolean injectorEnabled = true;

  public DdiPropertyInjector(PropertyInjector delegate, Class<?> clazz) {
    this.delegate = delegate;
    this.clazz = clazz;

    if (new JaxRsActivator().getClasses().contains(clazz)) {
      injectorEnabled = false;
    }
  }

  @Override
  public void inject(Object target) {
    if (injectorEnabled) {
      delegate.inject(target);
    }
  }

  @Override
  public void inject(HttpRequest request, HttpResponse response, Object target) throws Failure, WebApplicationException, ApplicationException {
    if (injectorEnabled) {
      delegate.inject(request, response, target);
    }
  }

  @Override
  public String toString() {
    return "DdiPropertyInjector (enabled: " + injectorEnabled + ") for " + clazz;
  }
}

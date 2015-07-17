/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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

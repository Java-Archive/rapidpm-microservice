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
import org.rapidpm.ddi.DI;

import javax.ws.rs.WebApplicationException;
import java.lang.reflect.Type;

/**
 * This ConstructorInjector implementation uses DDI to obtain
 * a contextual instance of a bean.
 *
 * @author Sven Ruppert
 */
public class DdiConstructorInjector implements ConstructorInjector {
  public static final Object[] OBJECTS = new Object[0];

  private final Type type;

  public DdiConstructorInjector(Type type) {
    this.type = type;
  }

  public Object construct() {
    return DI.activateDI((Class) type);
  }

  public Object construct(HttpRequest request, HttpResponse response) throws Failure, WebApplicationException, ApplicationException {
    return construct();
  }

  public Object[] injectableArguments() {
    return OBJECTS;
  }

  public Object[] injectableArguments(HttpRequest request, HttpResponse response) throws Failure {
    return injectableArguments();
  }
}

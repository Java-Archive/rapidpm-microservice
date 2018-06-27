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
package org.rapidpm.microservice.test;

import org.jboss.resteasy.test.TestPortProvider;
import org.rapidpm.dependencies.core.net.PortUtils;

import javax.ws.rs.Path;

public class RestUtils {

  public static final String RESTEASY_PORT_PROPERTY = "org.jboss.resteasy.port";
  public static final String RESTEASY_HOST_PROPERTY = "org.jboss.resteasy.host";
  public static final String REST_PORT_PROPERTY = "org.rapidpm.microservice.rest.port";
  public static final String REST_HOST_PROPERTY = "org.rapidpm.microservice.rest.host";


  public void setAllForLocalHost() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(REST_HOST_PROPERTY, "127.0.0.1");
  }


  public void setRestEasyPropertys(String host, String port) {
    System.setProperty(RESTEASY_PORT_PROPERTY, port);
    System.setProperty(RESTEASY_HOST_PROPERTY, host);
  }

  public void setRestEasyPropertys(String port) {
    System.setProperty(RESTEASY_PORT_PROPERTY, port);
  }


  public String generateBasicReqURL(Class restClass, String restAppPath) {
    if (restClass.isAnnotationPresent(Path.class)) {
      final Path path = (Path) restClass.getAnnotation(Path.class);
      final String ressourcePath = path.value();
      return TestPortProvider.generateURL(restAppPath + ressourcePath);
    }
    throw new RuntimeException("Class without Path Annotation " + restClass);
  }

}

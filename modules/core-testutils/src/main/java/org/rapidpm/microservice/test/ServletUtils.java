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

import org.rapidpm.dependencies.core.net.PortUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

public class ServletUtils {

  public static final String SERVLET_PORT_PROPERTY = "org.rapidpm.microservice.servlet.port";
  public static final String SERVLET_HOST_PROPERTY = "org.rapidpm.microservice.servlet.host";


  public void setServletPropertys(String host, String port) {
    System.setProperty(SERVLET_PORT_PROPERTY, port);
    System.setProperty(SERVLET_HOST_PROPERTY, host);
  }

  public void setServletPropertys(String port) {
    System.setProperty(SERVLET_PORT_PROPERTY, port);
  }

  public void setAllForLocalHost() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(SERVLET_HOST_PROPERTY, "127.0.0.1");
  }


  public String generateBasicReqURL(Class<? extends HttpServlet> servletClass, String servletPath) {
    if (servletClass.isAnnotationPresent(WebServlet.class)) {
      final WebServlet annotation = servletClass.getAnnotation(WebServlet.class);
      final String urlPattern = annotation.urlPatterns()[0];
      return "http://"
          + System.getProperty(SERVLET_HOST_PROPERTY) + ":"
          + System.getProperty(SERVLET_PORT_PROPERTY)
          + servletPath
          + urlPattern;
    }
    throw new RuntimeException("Class without WebServlet Annotation " + servletClass);
  }
}

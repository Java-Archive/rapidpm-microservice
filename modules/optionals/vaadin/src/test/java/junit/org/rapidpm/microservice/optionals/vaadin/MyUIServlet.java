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

package junit.org.rapidpm.microservice.optionals.vaadin;

import com.vaadin.annotations.VaadinServletConfiguration;
import org.rapidpm.microservice.optionals.vaadin.DDIVaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true, displayName = "Exampl002")
@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
@WebInitParam(name = "Resources", value = "http://virit.in/dawn/11")  //.addInitParam("Resources", "http://virit.in/dawn/11"))
public class MyUIServlet extends DDIVaadinServlet {


  @Override
  protected void servletInitialized() throws ServletException {
    super.servletInitialized();
  }

  @Override
  public List<String> topLevelPackagesToActivate() {
    return Arrays.asList("org.rapipm", "junit.org.rapidpm");
  }

}

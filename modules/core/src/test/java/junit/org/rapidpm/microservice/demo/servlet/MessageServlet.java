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

package junit.org.rapidpm.microservice.demo.servlet;


import junit.org.rapidpm.microservice.demo.service.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebInitParam(value = "Hello World", name = "message")
@WebServlet(urlPatterns = "/test")
public class MessageServlet extends HttpServlet {


//  @Inject Service service;

  public static final String MESSAGE = "message";

  private String message;

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);
    message = config.getInitParameter(MESSAGE);
  }

//  @Inject Service service;

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter writer = resp.getWriter();
//    writer.write(message + " "+  service.doWork());
    writer.write(message + " " + new Service().doWork());
    writer.close();
  }

  @Override
  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
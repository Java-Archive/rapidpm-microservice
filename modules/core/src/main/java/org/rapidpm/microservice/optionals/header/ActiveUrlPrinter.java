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

package org.rapidpm.microservice.optionals.header;

import org.rapidpm.microservice.optionals.ActiveUrlsHolder;

public class ActiveUrlPrinter {

  public void printActiveURLs(ActiveUrlsHolder activeUrlsHolder) {
    System.out.println("================= Deployment Summary ================= ");
    System.out.println("Sum Servlets                   = " + activeUrlsHolder.getServletCounter());
    System.out.println("Sum RestEndpoints              = " + activeUrlsHolder.getRestUrls().size());
    System.out.println("Sum RestEndpoints (Singletons) = " + activeUrlsHolder.getSingletonUrls().size());
    System.out.println("================= Deployment Summary ================= ");

    System.out.println("");
    System.out.println("List Servlet - URLs ");

    activeUrlsHolder.getServletUrls().forEach(System.out::println);

    System.out.println("");
    System.out.println("List RestEndpoint - URLs");
    activeUrlsHolder.getRestUrls().forEach(System.out::println);

    System.out.println("");
    System.out.println("List RestEndpoints (Singletons) URLs");
    activeUrlsHolder.getSingletonUrls().forEach(System.out::println);
  }
}

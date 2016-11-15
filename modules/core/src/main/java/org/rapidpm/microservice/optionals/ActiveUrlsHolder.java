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

package org.rapidpm.microservice.optionals;

import java.util.ArrayList;
import java.util.List;

public class ActiveUrlsHolder {

  private final List<String> restUrls = new ArrayList<>();
  private final List<String> servletUrls = new ArrayList<>();
  private final List<String> singletonUrls = new ArrayList<>();
  private long servletCounter;

  public ActiveUrlsHolder setServletCount(long counter) {
    servletCounter = counter;
    return this;
  }

  public ActiveUrlsHolder addRestUrl(String url) {
    restUrls.add(url);
    return this;
  }

  public ActiveUrlsHolder addServletUrl(String url) {
    servletUrls.add(url);
    return this;
  }

  public ActiveUrlsHolder addSingletonUrl(String url) {
    singletonUrls.add(url);
    return this;
  }

  public List<String> getRestUrls() {
    return restUrls;
  }

  public List<String> getServletUrls() {
    return servletUrls;
  }

  public List<String> getSingletonUrls() {
    return singletonUrls;
  }

  public long getServletCounter() {
    return servletCounter;
  }
}

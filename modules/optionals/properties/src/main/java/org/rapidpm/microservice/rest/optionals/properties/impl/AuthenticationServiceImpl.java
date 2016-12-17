package org.rapidpm.microservice.rest.optionals.properties.impl;

import org.rapidpm.microservice.rest.optionals.properties.api.AuthenticationService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by RapidPM - Team on 16.12.2016.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

  private List<String> knownClients;
  private boolean active = false;

  @PostConstruct
  public void init() {
    if (System.getProperty(CLIENTFILE) != null) {
      initAllowedClients();
      active = true;
    }
  }

  private void initAllowedClients() {
    final String path = System.getProperty(CLIENTFILE);
    try (Stream<String> lines = Files.lines(Paths.get(path))) {
      knownClients = lines.collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public boolean authenticate(String client) {
    return knownClients.contains(client);
  }

  @Override public boolean isActive() {
    return active;
  }


}

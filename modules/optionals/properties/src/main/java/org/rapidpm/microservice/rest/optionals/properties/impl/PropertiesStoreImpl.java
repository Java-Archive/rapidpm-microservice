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
package org.rapidpm.microservice.rest.optionals.properties.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.rapidpm.microservice.rest.optionals.properties.api.PropertiesStore;

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
 * Created by RapidPM - Team on 13.12.2016.
 */
public class PropertiesStoreImpl implements PropertiesStore {

  private Map<String, String> propertyMap;


  @PostConstruct
  private void initPropertiesStore() {
    propertyMap = new ConcurrentHashMap<>();
    loadProperties();
  }


  @Override
  public Optional<String> getProperty(String name) {
    return Optional.ofNullable(propertyMap.get(name));
  }

  @Override
  public Map<String, String> getPropertiesOfNamespace(String namespace) {
    return propertyMap.entrySet().stream()
                      .filter(entry -> entry.getKey().startsWith(namespace))
                      .collect(Collectors.toMap(Map.Entry::getKey , Map.Entry::getValue));
  }

  private void loadProperties() {
    final String path = System.getProperty(PROPERTYFILE);
    try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
      Properties props = new Properties();
      props.load(reader);

      props.entrySet()
           .forEach(entry -> propertyMap.putIfAbsent(entry.getKey().toString() , entry.getValue().toString()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

package org.rapidpm.microservice.propertyservice.persistence;

import java.util.Map;


public interface PropertiesLoader {
  Map<String, String> load(String path);

  Map<String, String> load(String file, String scope);

  void store(Map<String, String> propertiesMap, String path);
}

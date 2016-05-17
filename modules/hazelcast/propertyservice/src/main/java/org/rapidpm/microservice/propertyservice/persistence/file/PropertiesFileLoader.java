package org.rapidpm.microservice.propertyservice.persistence.file;


import org.rapidpm.microservice.propertyservice.persistence.PropertiesLoader;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesFileLoader implements PropertiesLoader {

  @Override
  public Map<String, String> load(String path) {
    final Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(path));
    } catch (IOException e) {
      // Todo logging
      e.printStackTrace();
    }

    final Map<String, String> resultMap = new HashMap<>(properties.size());
    properties
        .stringPropertyNames()
        .forEach(name ->
            resultMap.put(name, String.valueOf(properties.get(name)))
        );
    return resultMap;
  }

  @Override
  public Map<String, String> load(String file, String scope) {
    final Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(file + "/" + scope + ".properties"));
    } catch (IOException e) {
      // Todo logging
      e.printStackTrace();
    }

    final Map<String, String> resultMap = new HashMap<>(properties.size());
    properties
        .stringPropertyNames()
        .forEach(name ->
            resultMap.put(name, String.valueOf(properties.get(name)))
        );
    return resultMap;
  }

  @Override
  public void store(Map<String, String> propertiesMap, String path) {
    final Properties properties = new Properties();
    propertiesMap
        .keySet()
        .forEach(key -> properties.put(key, propertiesMap.get(key)));
    try {
      properties.store(new FileWriter(path), "");
    } catch (IOException e) {
      // Todo logging
      e.printStackTrace();
    }
  }

}

package org.rapidpm.microservice.propertyservice.persistence.file;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesFileLoader {

  public Map<String, String> load(String path) {
    final Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(path));
    } catch (IOException e) {
      // Todo logging
      e.printStackTrace();
    }

    final Map<String, String> resultMap = new HashMap<>(properties.size());
    properties.stringPropertyNames().forEach(name ->
            resultMap.put(name, String.valueOf(properties.get(name)))
    );
    return resultMap;
  }

  public void store(Map<String, String> propertieMap, String path) {
    final Properties properties = new Properties();
    propertieMap.keySet().forEach(key -> properties.put(key, propertieMap.get(key)));
    try {
      properties.store(new FileWriter(path), "");
    }  catch (IOException e) {
      // Todo logging
      e.printStackTrace();
    }
  }

}

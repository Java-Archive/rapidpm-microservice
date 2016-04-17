package org.rapidpm.microservice.propertyservice.impl;


import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.microservice.propertyservice.persistence.PropertiesLoader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertyService {

  public static final String PROPERTIES = "properties";
  private HazelcastInstance hazelcastInstance;
  private Map<String, String> properties;
  private boolean isRunning = false;

  @Inject
  PropertiesLoader propertiesLoader;

  public void init(@Nullable String source) {
    hazelcastInstance = Hazelcast.newHazelcastInstance();
    properties = hazelcastInstance.getReplicatedMap("properties");

    if (source != null && !source.isEmpty() && properties.isEmpty())
      properties.putAll(propertiesLoader.load(source));
    isRunning = true;
  }

  public void initFromCmd() {
    hazelcastInstance = Hazelcast.newHazelcastInstance();
    properties = hazelcastInstance.getReplicatedMap(PROPERTIES);
    //properties.putAll(propertiesLoader.load(System.getProperty("file")));
    isRunning = true;
  }

  public String loadProperties(String scope) {
    if (!isRunning()) {
      initFromCmd();
    }
    properties.putAll(propertiesLoader.load(System.getProperty("file"), scope));

    return "success";
  }

  public String getSingleProperty(String property) {
    if (properties.containsKey(property))
      return properties.get(property);
    else
      return ""; // Todo return something useful or make optional
  }

  public Set<String> getIndexOfLoadedProperties() {
    return properties.keySet();
  }

  public Set<String> getIndexOfScope(String scope) {
    return properties.keySet()
        .stream()
        .filter(key -> key.startsWith(scope))
        .collect(Collectors.toSet());
  }

  public Map<String, String> getPropertiesOfScope(String scope) {
    final Map<String, String> domainProperties = new HashMap<>();
    properties.keySet()
        .stream()
        .filter(key -> key.startsWith(scope))
        .forEach(key -> domainProperties.put(key, properties.get(key)));
    return domainProperties;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void shutdown() {
    hazelcastInstance.shutdown();
  }
}

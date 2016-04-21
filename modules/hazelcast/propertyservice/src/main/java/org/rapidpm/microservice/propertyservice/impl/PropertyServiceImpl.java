package org.rapidpm.microservice.propertyservice.impl;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.persistence.PropertiesLoader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertyServiceImpl implements PropertyService {

  public static final String DEFAULT_MAPNAME = "properties";
  public static final String MAPNAME = "mapname";
  private HazelcastInstance hazelcastInstance;
  private Map<String, String> properties;
  private boolean isRunning = false;

  @Inject
  PropertiesLoader propertiesLoader;

  @Override
  public void init(@Nullable String source) {
    if (System.getProperty("distributed", "") == "true") {
      hazelcastInstance = Hazelcast.newHazelcastInstance(getDefaultConfig());
      properties = hazelcastInstance.getReplicatedMap(getMapName());
    } else {
      properties = new HashMap<>();
    }

    if (source != null && !source.isEmpty() && properties.isEmpty())
      properties.putAll(propertiesLoader.load(source));
    isRunning = true;
  }

  @Override
  public void initFromCmd() {
    if (System.getProperty("distributed", "") == "true") {
      hazelcastInstance = Hazelcast.newHazelcastInstance(getDefaultConfig());
      properties = hazelcastInstance.getReplicatedMap(getMapName());
    } else {
      properties = new HashMap<>();
    }
    isRunning = true;
  }

  private Config getDefaultConfig() {
    final Config config = new Config();
    config.setProperty("hazelcast.network.tcpip", "true");
    config.setProperty("hazelcast.network.multicast", "false");
    return config;
  }

  @Override
  public String loadProperties(String scope) {
    if (!isRunning) {
      initFromCmd();
    }
    properties.putAll(propertiesLoader.load(System.getProperty("file"), scope));

    return "success";
  }

  @Override
  public String getSingleProperty(String property) {
    if (properties.containsKey(property))
      return properties.get(property);
    else
      return ""; // Todo return something useful or make optional
  }

  @Override
  public Set<String> getIndexOfLoadedProperties() {
    return properties.keySet();
  }

  @Override
  public Set<String> getIndexOfScope(String scope) {
    return properties.keySet()
        .stream()
        .filter(key -> key.startsWith(scope))
        .collect(Collectors.toSet());
  }

  @Override
  public Map<String, String> getPropertiesOfScope(String scope) {
    final Map<String, String> domainProperties = new HashMap<>();
    properties.keySet()
        .stream()
        .filter(key -> key.startsWith(scope))
        .forEach(key -> domainProperties.put(key, properties.get(key)));
    return domainProperties;
  }

  @Override
  public void forget() {
    if (properties != null && !properties.isEmpty())
      properties.clear();
  }

  @Override
  public boolean isRunning() {
    return isRunning;
  }

  @Override
  public void shutdown() {
    hazelcastInstance.shutdown();
  }

  private String getMapName() {
    if (System.getProperty(MAPNAME) != null)
      return System.getProperty("mapname");
    else
      return DEFAULT_MAPNAME;
  }

}

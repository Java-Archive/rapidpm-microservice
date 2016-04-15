package org.rapidpm.microservice.propertyservice.impl;


import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertyServiceImpl {

  private HazelcastInstance hazelcastInstance;
  private Map<String, String> properties;

  public void init() {
    hazelcastInstance = Hazelcast.newHazelcastInstance();
    properties = hazelcastInstance.getReplicatedMap("properties");

    if (properties.isEmpty())
      loadProperties();
  }

  private void loadProperties() {
    properties.put("example.part01.001", "test001");
    properties.put("example.part01.002", "test002");
    properties.put("single.theonlykey", "theonlyvalue");
  }


  public String getSingleProperty(String property) {
    if (properties.containsKey(property))
      return properties.get(property);
    else
      return "";
  }

  public Set<String> getIndex() {
    return properties.keySet();
  }

  public Set<String> getIndexToDomain(String domain) {
    final Set<String> domainIndex =
        properties.keySet()
            .stream()
            .filter(key -> key.startsWith(domain))
            .collect(Collectors.toSet());
    return domainIndex;
  }

  public Map<String, String> getPropertiesOfDomain(String domain) {
    final Map<String, String> domainProperties = new HashMap<>();
    properties.keySet()
        .stream()
        .filter(key -> key.startsWith(domain))
        .forEach(key -> domainProperties.put(key, properties.get(key)));
    return domainProperties;
  }

}

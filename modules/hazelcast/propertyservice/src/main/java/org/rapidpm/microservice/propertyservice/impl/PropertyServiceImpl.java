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

public class PropertyServiceImpl {

  private HazelcastInstance hazelcastInstance;
  private Map<String, String> properties;
  @Inject
  PropertiesLoader propertiesLoader;

  public void init(@Nullable String source) {
    hazelcastInstance = Hazelcast.newHazelcastInstance();
    properties = hazelcastInstance.getReplicatedMap("properties");

    if (source != null && !source.isEmpty() && properties.isEmpty())
      properties.putAll(propertiesLoader.load(source));
  }


  public String getSingleProperty(String property) {
    if (properties.containsKey(property))
      return properties.get(property);
    else
      return ""; // Todo return something useful or make optional
  }

  public Set<String> getIndex() {
    return properties.keySet();
  }

  public Set<String> getIndexToDomain(String domain) {
    return properties.keySet()
        .stream()
        .filter(key -> key.startsWith(domain))
        .collect(Collectors.toSet());
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

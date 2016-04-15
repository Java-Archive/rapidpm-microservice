package junit.org.rapidpm.microservice.propertyservice.impl;


import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Set;

public class PropertyServiceImpl {

  private HazelcastInstance hazelcastInstance;
  private Map<String, String> properties;

  public void init() {
    hazelcastInstance = Hazelcast.newHazelcastInstance();
    properties = hazelcastInstance.getReplicatedMap("properties");
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

  public Set<String> getIndexToDomain() {
    // Todo
    return null;
  }

  public Map<String, String> getPropertiesOfDomain(String domain) {
    // Todo
    return null;
  }

}

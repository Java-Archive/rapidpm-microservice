package org.rapidpm.microservice.optionals.index.stores;


import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfType;

import java.util.Set;

/**
 * Created by Sven Ruppert on 19.01.16.
 */
public interface IndexStore {

  <I extends IndexOfType<T>, T> void addIndexOfType(String indexName, I indexOfType);

  boolean containsIndex(String indexName);

  boolean removeIndex(String indexName);

  void shutdownIndex(String indexName);

  void shutdownAll();

  Set<String> getIndexNameSet();


  <I extends IndexOfType<T>, T> I getIndex(String indexName);

}

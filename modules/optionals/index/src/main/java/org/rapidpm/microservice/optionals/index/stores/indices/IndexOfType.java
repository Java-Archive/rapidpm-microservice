package org.rapidpm.microservice.optionals.index.stores.indices;

import java.util.List;

/**
 * Created by Sven Ruppert on 18.01.16.
 */
public interface IndexOfType<T> {

  void addElement(T t);

  List<T> queryByExample(T t);

  List<T> query(String query);

  void shutdown();

}

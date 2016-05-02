/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.optionals.index.stores;

import org.rapidpm.dependencies.core.fs.DirectoryUtils;
import org.rapidpm.microservice.optionals.index.IndexManagement;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultIndexStore implements IndexStore {

  private final Map<String, IndexOfType> indexOfTypeMap = new HashMap<>();

  @Override
  public <I extends IndexOfType<T>, T> void addIndexOfType(final String indexName, final I indexOfType) {
    indexOfTypeMap.putIfAbsent(indexName, indexOfType);
  }

  @Override
  public boolean containsIndex(String indexName) {
    if (indexName != null) {
      return indexOfTypeMap.containsKey(indexName);
    } else {
      return false;
    }
  }

  @Override
  public boolean removeIndex(final String indexName) {
    if (containsIndex(indexName)) {
      final IndexOfType indexOfType = indexOfTypeMap.get(indexName);
      indexOfType.shutdown();
      indexOfTypeMap.remove(indexName);

      return deleteIndexDirectory(indexName);
    } else {
      return false;
    }
  }

  private boolean deleteIndexDirectory(final String indexName) {
    return new DirectoryUtils()
        .deleteIndexDirectory(IndexManagement.IDX_BASE_DIR + "/" + indexName);
  }


  @Override
  public void shutdownIndex(final String indexName) {
    if (containsIndex(indexName)) {
      indexOfTypeMap
          .get(indexName)
          .shutdown();
    }
  }

  @Override
  public void shutdownAll() {
    indexOfTypeMap
        .values()
        .stream()
        .forEach(IndexOfType::shutdown);
  }


  @Override
  public Set<String> getIndexNameSet() {
    return Collections.unmodifiableSet(indexOfTypeMap.keySet());
  }

  @Override
  public <I extends IndexOfType<T>, T> I getIndex(final String indexName) {
    return (I) indexOfTypeMap.get(indexName);
  }


}

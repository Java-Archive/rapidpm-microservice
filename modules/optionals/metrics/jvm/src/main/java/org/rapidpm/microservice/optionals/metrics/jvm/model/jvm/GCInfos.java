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

package org.rapidpm.microservice.optionals.metrics.jvm.model.jvm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class GCInfos implements Serializable {

  private long collectionCount;
  private long collectionTime;
  private String[] memoryPoolNames;
  private String name;
  private String objectName;


  public GCInfos collectionCount(final long collectionCount) {
    this.collectionCount = collectionCount;
    return this;
  }

  public GCInfos collectionTime(final long collectionTime) {
    this.collectionTime = collectionTime;
    return this;
  }

  public GCInfos memoryPoolNames(final String[] memoryPoolNames) {
    this.memoryPoolNames = memoryPoolNames;
    return this;
  }

  public GCInfos name(final String name) {
    this.name = name;
    return this;
  }

  public GCInfos objectName(final String objectName) {
    this.objectName = objectName;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionCount, collectionTime, memoryPoolNames, name, objectName);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof GCInfos)) return false;
    final GCInfos gcInfos = (GCInfos) o;
    return collectionCount == gcInfos.collectionCount &&
        collectionTime == gcInfos.collectionTime &&
        Arrays.equals(memoryPoolNames, gcInfos.memoryPoolNames) &&
        Objects.equals(name, gcInfos.name) &&
        Objects.equals(objectName, gcInfos.objectName);
  }

  @Override
  public String toString() {
    return "GCInfos{" +
        "collectionCount=" + collectionCount +
        ", collectionTime=" + collectionTime +
        ", memoryPoolNames=" + Arrays.toString(memoryPoolNames) +
        ", name='" + name + '\'' +
        ", objectName='" + objectName + '\'' +
        '}';
  }

  public long getCollectionCount() {
    return collectionCount;
  }

  public long getCollectionTime() {
    return collectionTime;
  }

  public String[] getMemoryPoolNames() {
    return memoryPoolNames;
  }

  public String getName() {
    return name;
  }

  public String getObjectName() {
    return objectName;
  }
}
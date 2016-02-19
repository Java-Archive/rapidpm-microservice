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
import java.lang.management.MemoryUsage;
import java.util.Objects;

public class MemoryInfos implements Serializable {
  private long f;
  private long m;
  private long t;
  private int o;
  private MemoryUsage heapMemoryUsage;
  private MemoryUsage nonHeapMemoryUsage;

  @Override
  public int hashCode() {
    return Objects.hash(f, m, o, t, heapMemoryUsage, nonHeapMemoryUsage);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof MemoryInfos)) return false;
    final MemoryInfos that = (MemoryInfos) o;
    return f == that.f &&
        m == that.m &&
        t == that.t &&
        Objects.equals(heapMemoryUsage, that.heapMemoryUsage) &&
        Objects.equals(nonHeapMemoryUsage, that.nonHeapMemoryUsage);
  }

  @Override
  public String toString() {
    return "MemoryInfos{" +
        "freeMem=" + f + System.lineSeparator() +
        ", heapMemoryUsage=" + heapMemoryUsage + System.lineSeparator() +
        ", maxMem=" + m + System.lineSeparator() +
        ", objectPendingFinalizationCount=" + o + System.lineSeparator() +
        ", nonHeapMemoryUsage=" + nonHeapMemoryUsage + System.lineSeparator() +
        ", totalMem=" + t + System.lineSeparator() +
        '}';
  }

  public MemoryInfos freeMemory(final long freeMemory) {
    this.f = freeMemory;
    return this;
  }

  public MemoryInfos maxMemory(final long maxMemory) {
    this.m = maxMemory;
    return this;
  }

  public MemoryInfos totalMemory(final long totalMemory) {
    this.t = totalMemory;
    return this;
  }

  public long getFreeMemory() {
    return f;
  }

  public long getMaxMemory() {
    return m;
  }

  public long getTotalMemory() {
    return t;
  }

  public MemoryUsage getHeapMemoryUsage() {
    return heapMemoryUsage;
  }

  public MemoryUsage getNonHeapMemoryUsage() {
    return nonHeapMemoryUsage;
  }

  public MemoryInfos heapMemoryUsage(final MemoryUsage heapMemoryUsage) {
    this.heapMemoryUsage = heapMemoryUsage;
    return this;
  }

  public MemoryInfos nonHeapMemoryUsage(final MemoryUsage nonHeapMemoryUsage) {
    this.nonHeapMemoryUsage = nonHeapMemoryUsage;
    return this;
  }

  public MemoryInfos objectPendingFinalizationCount(final int objectPendingFinalizationCount) {
    this.o = objectPendingFinalizationCount;
    return this;
  }
}

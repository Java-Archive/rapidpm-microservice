/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.optionals.metrics.jvm.model.jvm;


import java.io.Serializable;
import java.lang.management.MemoryUsage;
import java.util.Objects;

public class MemoryInfos implements Serializable {
  private long freeMem;
  private long maxMem;
  private long totalMem;
  private int objectPendingFinalizationCount;
  private MemoryUsage heapMemoryUsage;
  private MemoryUsage nonHeapMemoryUsage;

  @Override
  public int hashCode() {
    return Objects.hash(freeMem, maxMem, objectPendingFinalizationCount, totalMem, heapMemoryUsage, nonHeapMemoryUsage);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof MemoryInfos)) return false;
    final MemoryInfos that = (MemoryInfos) o;
    return freeMem == that.freeMem &&
        maxMem == that.maxMem &&
        totalMem == that.totalMem &&
        Objects.equals(heapMemoryUsage, that.heapMemoryUsage) &&
        Objects.equals(nonHeapMemoryUsage, that.nonHeapMemoryUsage);
  }

  @Override
  public String toString() {
    return "MemoryInfos{" +
        "freeMem=" + freeMem + System.lineSeparator() +
        ", heapMemoryUsage=" + heapMemoryUsage + System.lineSeparator() +
        ", maxMem=" + maxMem + System.lineSeparator() +
        ", objectPendingFinalizationCount=" + objectPendingFinalizationCount + System.lineSeparator() +
        ", nonHeapMemoryUsage=" + nonHeapMemoryUsage + System.lineSeparator() +
        ", totalMem=" + totalMem + System.lineSeparator() +
        '}';
  }

  public MemoryInfos freeMemory(final long freeMemory) {
    this.freeMem = freeMemory;
    return this;
  }

  public MemoryInfos maxMemory(final long maxMemory) {
    this.maxMem = maxMemory;
    return this;
  }

  public MemoryInfos totalMemory(final long totalMemory) {
    this.totalMem = totalMemory;
    return this;
  }

  public long getFreeMemory() {
    return freeMem;
  }

  public long getMaxMemory() {
    return maxMem;
  }

  public long getTotalMemory() {
    return totalMem;
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
    this.objectPendingFinalizationCount = objectPendingFinalizationCount;
    return this;
  }
}

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

import java.util.Objects;

public class SpecInfos {
  private String name;
  private String vendor;
  private String version;

  @Override
  public int hashCode() {
    return Objects.hash(name, vendor, version);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof SpecInfos)) return false;
    final SpecInfos specInfos = (SpecInfos) o;
    return Objects.equals(name, specInfos.name) &&
        Objects.equals(vendor, specInfos.vendor) &&
        Objects.equals(version, specInfos.version);
  }

  @Override
  public String toString() {
    return "SpecInfos{" +
        "specName='" + name + '\'' +
        ", specVendor='" + vendor + '\'' +
        ", specVersion='" + version + '\'' +
        '}';
  }

  public SpecInfos specName(final String specName) {
    this.name = specName;
    return this;
  }

  public SpecInfos specVendor(final String specVendor) {
    this.vendor = specVendor;
    return this;
  }

  public SpecInfos specVersion(final String specVersion) {
    this.version = specVersion;
    return this;
  }
}

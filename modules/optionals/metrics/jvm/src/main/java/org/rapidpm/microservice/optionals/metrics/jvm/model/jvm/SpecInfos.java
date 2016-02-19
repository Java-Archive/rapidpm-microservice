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
  private String n;
  private String v;
  private String ver;

  @Override
  public int hashCode() {
    return Objects.hash(n, v, ver);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof SpecInfos)) return false;
    final SpecInfos specInfos = (SpecInfos) o;
    return Objects.equals(n, specInfos.n) &&
        Objects.equals(v, specInfos.v) &&
        Objects.equals(ver, specInfos.ver);
  }

  @Override
  public String toString() {
    return "SpecInfos{" +
        "specName='" + n + '\'' +
        ", specVebdor='" + v + '\'' +
        ", specVersion='" + ver + '\'' +
        '}';
  }

  public SpecInfos specName(final String specName) {
    this.n = specName;
    return this;
  }

  public SpecInfos specVendor(final String specVendor) {
    this.v = specVendor;
    return this;
  }

  public SpecInfos specVersion(final String specVersion) {
    this.ver = specVersion;
    return this;
  }
}

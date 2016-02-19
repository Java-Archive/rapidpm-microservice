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

public class OSInfos {

  private String name;
  private String arch;
  private String version;
  private int processors;

  @Override
  public int hashCode() {
    return Objects.hash(name, arch, version, processors);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof OSInfos)) return false;
    final OSInfos osInfos = (OSInfos) o;
    return processors == osInfos.processors &&
        Objects.equals(name, osInfos.name) &&
        Objects.equals(arch, osInfos.arch) &&
        Objects.equals(version, osInfos.version);
  }

  @Override
  public String toString() {
    return "OSInfos{" +
        "arch='" + arch + '\'' +
        ", name='" + name + '\'' +
        ", processors=" + processors +
        ", version='" + version + '\'' +
        '}';
  }

  public OSInfos name(final String name) {
    this.name = name;
    return this;
  }

  public OSInfos arch(final String arch) {
    this.arch = arch;
    return this;
  }

  public OSInfos version(final String version) {
    this.version = version;
    return this;
  }

  public OSInfos processors(final int processors) {
    this.processors = processors;
    return this;
  }

  private String getName() {
    return name;
  }

  private String getArch() {
    return arch;
  }

  private String getVersion() {
    return version;
  }

  private int getProcessors() {
    return processors;
  }
}

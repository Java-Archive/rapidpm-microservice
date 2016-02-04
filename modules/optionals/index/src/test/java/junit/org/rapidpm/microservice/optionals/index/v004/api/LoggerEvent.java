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

package junit.org.rapidpm.microservice.optionals.index.v004.api;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class LoggerEvent implements Serializable {


  private String level;
  private String message;
  private LocalDateTime timestamp;

  public LoggerEvent level(final String level) {
    this.level = level;
    return this;
  }

  public LoggerEvent message(final String message) {
    this.message = message;
    return this;
  }

  public LoggerEvent timestamp(final LocalDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(level, message, timestamp);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof LoggerEvent)) return false;
    final LoggerEvent that = (LoggerEvent) o;
    return Objects.equals(level, that.level) &&
        Objects.equals(message, that.message) &&
        Objects.equals(timestamp, that.timestamp);
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(final String level) {
    this.level = level;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(final LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}

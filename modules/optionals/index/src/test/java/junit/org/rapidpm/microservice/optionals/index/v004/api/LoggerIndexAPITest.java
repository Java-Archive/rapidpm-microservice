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

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class LoggerIndexAPITest {

  @Test
  public void test001() throws Exception {
    final String luceneQuery = new IndexOfTypeLoggerEvent() {
      @Override
      public void addElement(final LoggerEvent loggerEvent) {

      }

      @Override
      public List<LoggerEvent> queryByExample(final LoggerEvent loggerEvent) {
        return null;
      }

      @Override
      public List<LoggerEvent> query(final String query) {
        return null;
      }

      @Override
      public void shutdown() {

      }
    }
        .toLuceneQuery(new LoggerEvent()
            .level("LEVEL")
            .message("Message")
            .timestamp(LocalDateTime.now()));
    System.out.println("luceneQuery = " + luceneQuery);

  }
}

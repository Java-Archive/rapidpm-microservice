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

package junit.org.rapidpm.microservice.persistence.jdbc.dao.v001;

import org.jetbrains.annotations.NotNull;
import org.rapidpm.microservice.persistence.jdbc.dao.QueryOneValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.MyCustomizing.POOL_NAME;


public interface MyQueryOneValue<T> extends QueryOneValue<T> {

  @NotNull
  default String getPoolname() {
    return POOL_NAME;
  }

  @FunctionalInterface
  interface QueryOneString extends MyQueryOneValue<String> {
    default String getFirstElement(final ResultSet resultSet) throws SQLException {
      return resultSet.getString(1);
    }
  }
}

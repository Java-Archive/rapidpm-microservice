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

package org.rapidpm.microservice.persistence.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public interface QueryOneValue<T> extends BasicOperation {

  default Optional<T> execute(JDBCConnectionPools connectionPools) {
    final HikariDataSource dataSource = connectionPools.getDataSource(getPoolname());
    try {
      final Connection connection = dataSource.getConnection();
      try (final Statement statement = connection.createStatement()) {
        final String sql = createSQL();
        final ResultSet resultSet = statement.executeQuery(sql);
        final boolean next = resultSet.next();
        if (next) {
          final T value = getFirstElement(resultSet);

          final boolean error = resultSet.next();

          statement.close();
          dataSource.evictConnection(connection);

          if (error) throw new RuntimeException("too many values are selected with query");
          return Optional.of(value);
        } else {
          statement.close();
          dataSource.evictConnection(connection);
          return Optional.empty();
        }
      }
    } catch (final SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }


  T getFirstElement(final ResultSet resultSet) throws SQLException;

}

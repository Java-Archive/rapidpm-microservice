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

package junit.org.rapidpm.microservice.persistence.jdbc;

import org.apache.commons.io.IOUtils;
import org.hsqldb.server.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;
import org.rapidpm.microservice.test.PortUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class HsqlBaseTest {

  protected static Server hsqlServer;
  private static String url;
  private static int port;
  private final String[] scripts = createSQLInitScriptArray();

  // add here other *basic* scripts that should be executed
//  private final String[] scripts = {
//      "CLEAR_SCHEMA.sql", "CREATE_TABLE_EXAMPLE.sql"
//  };

  @BeforeClass
  public static void beforeClass() throws Exception {
    port = new PortUtils().nextFreePortForTest();
    url = "jdbc:hsqldb:mem://127.0.0.1:" + port + "/" + dbname();
    startServer();
    startPools();
  }

  public static String dbname() {
    return "testDB";
  }

  private static void startServer() {
    hsqlServer = new Server();
    hsqlServer.setDatabaseName(0, dbname());
    hsqlServer.setDatabasePath(0, "mem:target/" + dbname());
    hsqlServer.setPort(port);
    hsqlServer.setAddress("127.0.0.1");
    hsqlServer.setErrWriter(null);
    hsqlServer.setLogWriter(null);
    hsqlServer.setSilent(true);
    hsqlServer.setTrace(false);
    hsqlServer.start();

    hsqlServer.checkRunning(true);
//    logger.logDebug("server started");
  }

  private static void startPools() {
    JDBCConnectionPools pools = JDBCConnectionPools.instance();
    pools
        .addJDBCConnectionPool(poolname())
        .withJdbcURL(url)
        .withUsername(username())
        .withPasswd(password())
        .withTimeout(2000)
        .withAutoCommit(true)
        .done();
    // add more pools if needed
    pools.connectPools();
//    logger.logDebug("pools connected");
  }

  public static String poolname() {
    return "testDBPOOL";
  }

  public static String username() {
    return "SA";
  }

  public static String password() {
    return "";
  }

  @AfterClass
  public static void after() throws Exception {
    JDBCConnectionPools.instance().shutdownPools();
    hsqlServer.shutdown();
    hsqlServer.stop();
  }

  public abstract String[] createSQLInitScriptArray();

  @Before
  public void initSchema() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    activateDI4Packages();
    DI.activatePackages(this.getClass());
    DI.activateDI(this);

    for (final String script : scripts) {
      executeSqlScript(this.getClass().getResource(script).getPath());
    }

    final URL testSqlResource = getClass().getResource(getClass().getSimpleName() + ".sql");
    if (testSqlResource != null) {
      final String testSqlPath = testSqlResource.getPath();
      executeSqlScript(testSqlPath);
    } else {
      System.out.println("No SQL for " + getClass().getSimpleName());
    }
  }

  public abstract void activateDI4Packages();

  private void executeSqlScript(final String filePath) {
    try (
        final InputStream sqlAsStream = new FileInputStream(filePath);
        final Connection connection = JDBCConnectionPools.instance().getDataSource(poolname()).getConnection();
        final Statement statement = connection.createStatement()
    ) {
      final String sqlStatement = IOUtils.toString(sqlAsStream);
      statement.executeUpdate(sqlStatement);
      connection.commit();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

}

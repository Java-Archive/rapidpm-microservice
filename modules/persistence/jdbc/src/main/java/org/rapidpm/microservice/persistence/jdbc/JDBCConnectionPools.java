package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by svenruppert on 10.07.15.
 */
public class JDBCConnectionPools {

  private static final Map<String, JDBCConnectionPool> POOL_MAP = new ConcurrentHashMap<>();
  private static JDBCConnectionPools ourInstance = new JDBCConnectionPools();

  private JDBCConnectionPools() {
  }

  public static JDBCConnectionPools instance() {
    return ourInstance;
  }

  private JDBCConnectionPools withJDBCConnectionPool(JDBCConnectionPool pool) {
    JDBCConnectionPools.POOL_MAP.put(pool.getPoolname(), pool);
    return this;
  }

  public JDBCConnectionPool.Builder addJDBCConnectionPool(String poolname) {
    final JDBCConnectionPool.Builder builder = JDBCConnectionPool.newBuilder().withParentBuilder(this);
    return builder.withPoolname(poolname);
  }

  public void shutdownPools() {
    POOL_MAP.forEach((k, v) -> v.close());
  }

  public void connectPools() {
    POOL_MAP.forEach((k, v) -> v.connect());
  }


  public HikariDataSource getDataSource(String poolname) {
    return POOL_MAP.get(poolname).getDataSource();
  }

}

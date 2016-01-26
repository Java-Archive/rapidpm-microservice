package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPool.Builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Sven Ruppert on 10.07.15.
 */
public class JDBCConnectionPools {

  private static final Map<String, JDBCConnectionPool> POOL_MAP = new ConcurrentHashMap<>();
  private static final JDBCConnectionPools OUR_INSTANCE = new JDBCConnectionPools();

  private JDBCConnectionPools() {
  }

  public static JDBCConnectionPools instance() {
    return OUR_INSTANCE;
  }

  private JDBCConnectionPools withJDBCConnectionPool(JDBCConnectionPool pool) {
    POOL_MAP.put(pool.getPoolname(), pool);
    return this;
  }

  public Builder addJDBCConnectionPool(String poolname) {
    final Builder builder = JDBCConnectionPool.newBuilder().withParentBuilder(this);
    return builder.withPoolname(poolname);
  }

  public void shutdownPools() {
    POOL_MAP.forEach((k, v) -> v.close());
  }

  public void connectPools() {
    POOL_MAP.forEach((k, v) -> v.connect());
  }


  public HikariDataSource getDataSource(String poolname) {
    return  POOL_MAP.get(poolname) != null ? POOL_MAP.get(poolname).getDataSource() : null;
  }

}

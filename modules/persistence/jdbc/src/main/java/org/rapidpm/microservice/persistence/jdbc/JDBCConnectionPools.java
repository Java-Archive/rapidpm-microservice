package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by svenruppert on 10.07.15.
 */
@ThreadSafe
public class JDBCConnectionPools {

  private static final Map<String,JDBCConnectionPool> poolMap = new ConcurrentHashMap<>();

  private static JDBCConnectionPools ourInstance = new JDBCConnectionPools();

  public static Optional<HikariDataSource> getPool(String poolname) {
    return Optional.ofNullable(poolMap.get(poolname));
  }

  @GuardedBy(poolMap)
  public static


  private JDBCConnectionPools() {
  }



  public JDBCConnectionPool.Builder createNewPool(String poolname){
    return JDBCConnectionPool.newBuilder().withPoolname(poolname);
  }

  public void shutdownPools() {
    poolMap.forEach((k,v)-> v.close());
  }


  private HikariDataSource getDataSource() {
    return dataSource;
  }

  public Optional<Connection> getConnection() {
    try {
      final Connection connection = dataSource.getConnection();
      return Optional.of(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }







}

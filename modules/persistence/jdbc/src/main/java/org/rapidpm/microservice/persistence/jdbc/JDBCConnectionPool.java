package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.proxybuilder.basepattern.builder.NestedBuilder;

import javax.annotation.Nonnull;

/**
 * Created by svenruppert on 13.07.15.
 */
public class JDBCConnectionPool {

  private final String poolname;

  private final String jdbcURL;
  private final String username;
  private final String passwd;

  private final Integer timeout;
  private final boolean autoCommit;
  private final String sqlInit;
  private final String sqlTest;
  private final String jdbcDriverClassName;

  private HikariDataSource dataSource;

  private JDBCConnectionPool(final Builder builder) {
    poolname = builder.poolname;
    jdbcURL = builder.jdbcURL;
    username = builder.username;
    passwd = builder.passwd;
    autoCommit = builder.autoCommit;
    sqlInit = builder.sqlInit;
    sqlTest = builder.sqlTest;
    timeout = builder.timeout;
    jdbcDriverClassName = builder.jdbcDriverClassName;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void connect() {
    dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(jdbcURL);
    dataSource.setUsername(username);
    dataSource.setPoolName(poolname);
    dataSource.setAutoCommit(autoCommit);
    if (passwd != null) dataSource.setPassword(passwd);
    if (sqlInit != null) dataSource.setConnectionInitSql(sqlInit);
    if (sqlTest != null) dataSource.setConnectionTestQuery(sqlTest);
    if (timeout != null) dataSource.setConnectionTimeout(timeout);
    if (jdbcDriverClassName != null) dataSource.setDriverClassName(jdbcDriverClassName);
  }

  public void close() {
    dataSource.shutdown();
  }

  public String getPoolname() {
    return poolname;
  }

  public HikariDataSource getDataSource() {
    return dataSource;
  }

  public static final class Builder extends NestedBuilder<JDBCConnectionPools, JDBCConnectionPool> {
    private String poolname;
    private String jdbcURL;
    private String username;
    private String passwd;
    private boolean autoCommit;
    private String sqlInit;
    private String sqlTest;
    private Integer timeout;
    public String jdbcDriverClassName;

    private Builder() {
    }

    @Nonnull
    public Builder withPoolname(@Nonnull final String poolname) {
      this.poolname = poolname;
      return this;
    }

    @Nonnull
    public Builder withJdbcURL(@Nonnull final String jdbcURL) {
      this.jdbcURL = jdbcURL;
      return this;
    }

    @Nonnull
    public Builder withUsername(@Nonnull final String username) {
      this.username = username;
      return this;
    }

    @Nonnull
    public Builder withPasswd(@Nonnull final String passwd) {
      this.passwd = passwd;
      return this;
    }

    @Nonnull
    public Builder withAutoCommit(final boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    @Nonnull
    public Builder withSqlInit(@Nonnull final String sqlInit) {
      this.sqlInit = sqlInit;
      return this;
    }

    @Nonnull
    public Builder withSqlTest(@Nonnull final String sqlTest) {
      this.sqlTest = sqlTest;
      return this;
    }

    @Nonnull
    public Builder withTimeout(@Nonnull final Integer timeout) {
      this.timeout = timeout;
      return this;
    }

    @Nonnull
    public Builder withJdbcDriverClassName(String jdbcDriverClassName){
      this.jdbcDriverClassName = jdbcDriverClassName;
      return this;
    }

    @Nonnull
    public JDBCConnectionPool build() {
      final JDBCConnectionPool jdbcConnectionPool = new JDBCConnectionPool(this);
      return jdbcConnectionPool;
    }
  }
}

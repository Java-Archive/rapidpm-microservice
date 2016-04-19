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

import junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.MyQueryOneValue.QueryOneString;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;


public class UserDAO {

  @Inject JDBCConnectionPools connectionPools;

  public void writeUser(final User user) {
    ((MyUpdate) () -> createInsert(user)).update(connectionPools);
  }

  private String createInsert(final User user) {
    final String sql = "INSERT INTO CUSTOMER " +
        "( CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL ) " +
        " VALUES " +
        "( " +
        user.getCustomerID() + ", " +
        "'" + user.getFirstname() + "', " +
        "'" + user.getLastname() + "' , " +
        "'" + user.getEmail() + "' " +
        ")";
    return sql;
  }

  public Optional<User> readUser(int customerID) {
    return ((QueryUser) () -> getUserByCustomerID(customerID)).execute(connectionPools);
  }

  private String getUserByCustomerID(int customerID) {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL " +
        " FROM CUSTOMER " +
        " WHERE CUSTOMER_ID = " + customerID + "";
  }

  public Optional<String> readMailAddress(int customerID) {
    return ((QueryOneString) () -> getMailByCustomerID(customerID)).execute(connectionPools);
  }

  private String getMailByCustomerID(int customerID) {
    return "SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_ID = " + customerID + "";
  }

  public interface QueryUser extends MyQueryOneValue<User> {

    @Override
    default User getFirstElement(final ResultSet resultSet) throws SQLException {
      return new User(
          resultSet.getInt("CUSTOMER_ID"),
          resultSet.getString("FIRSTNAME"),
          resultSet.getString("LASTNAME"),
          resultSet.getString("EMAIL")
      );
    }
  }

  public static class User {

    private final Integer customerID;
    private final String firstname;
    private final String lastname;
    private final String email;

    public User(Integer customerID, String firstname, String lastname, String email) {
      this.customerID = customerID;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
    }

    public Integer getCustomerID() {
      return customerID;
    }

    public String getFirstname() {
      return firstname;
    }

    public String getLastname() {
      return lastname;
    }

    public String getEmail() {
      return email;
    }

    @Override
    public int hashCode() {
      return Objects.hash(customerID, firstname, lastname, email);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      final User user = (User) o;
      return Objects.equals(customerID, user.customerID) &&
          Objects.equals(firstname, user.firstname) &&
          Objects.equals(lastname, user.lastname) &&
          Objects.equals(email, user.email);
    }

    @Override
    public String toString() {
      return "User{" +
          "customerID=" + customerID +
          ", firstname='" + firstname + '\'' +
          ", lastname='" + lastname + '\'' +
          ", email=" + email +
          '}';
    }
  }

}

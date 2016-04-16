package org.rapidpm.microservice.propertyservice.persistence;

public enum Persistence {

  FILE("file"),
  DATABASE("database");

  private String value;

  Persistence(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return "Persistence{" +
        "value='" + value + '\'' +
        '}';
  }

}

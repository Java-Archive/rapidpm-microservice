package org.rapidpm.microservice.optionals.header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Ruppert on 20.11.15.
 */
public class ActiveUrlsHolder {

  private final List<String> restUrls = new ArrayList<>();
  private final List<String> servletUrls = new ArrayList<>();
  private final List<String> singletonUrls = new ArrayList<>();
  private long servletCounter;

  public ActiveUrlsHolder setServletCount(long counter) {
    servletCounter = counter;
    return this;
  }

  public ActiveUrlsHolder addRestUrl(String url) {
    restUrls.add(url);
    return this;
  }

  public ActiveUrlsHolder addServletUrl(String url) {
    servletUrls.add(url);
    return this;
  }

  public ActiveUrlsHolder addSingletonUrl(String url) {
    singletonUrls.add(url);
    return this;
  }

  public List<String> getRestUrls() {
    return restUrls;
  }

  public List<String> getServletUrls() {
    return servletUrls;
  }

  public List<String> getSingletonUrls() {
    return singletonUrls;
  }

  public long getServletCounter() {
    return servletCounter;
  }
}

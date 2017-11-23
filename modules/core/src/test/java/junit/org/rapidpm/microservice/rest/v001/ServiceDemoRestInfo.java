package junit.org.rapidpm.microservice.rest.v001;

import java.util.function.BiFunction;

/**
 *
 */
public interface ServiceDemoRestInfo {

  public static final String PATH_BASE           = "/demoservice";
  public static final String PATH_METHODE_DOWORK = "dowork";


  public default BiFunction<String, String, String> baseURL() {
    return (host , port) -> "http://" + host + ":" + port + "/rest/v001" + PATH_BASE;
  }

}

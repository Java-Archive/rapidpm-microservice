package junit.org.rapidpm.microservice.rest.v001;

/**
 *
 */
public class ServiceDemoRestClient implements ServiceDemo, ServiceDemoRestInfo {


  @Override
  public String doWork() {
    final String baseURL = baseURL().apply("127.0.0.1" , "8070");
    return null;
  }



}

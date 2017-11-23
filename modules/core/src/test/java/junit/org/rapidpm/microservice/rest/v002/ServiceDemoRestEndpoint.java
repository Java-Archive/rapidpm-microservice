package junit.org.rapidpm.microservice.rest.v002;

/**
 *
 */
public class ServiceDemoRestEndpoint implements ServiceDemoRest {

  public String doWork() {
    return "Hello Rest";
  }
}

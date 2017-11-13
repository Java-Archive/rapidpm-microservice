package junit.org.rapidpm.microservice.rest.v001;

import static junit.org.rapidpm.microservice.rest.v001.ServiceDemoRestInfo.PATH_BASE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 */
@Path(PATH_BASE)
public class ServiceDemoRestEndpoint implements ServiceDemo, ServiceDemoRestInfo {

  @Override
  @GET()
  @Produces("text/plain")
  @Path(PATH_METHODE_DOWORK)
  public String doWork() {
    return "Hello Rest";
  }
}

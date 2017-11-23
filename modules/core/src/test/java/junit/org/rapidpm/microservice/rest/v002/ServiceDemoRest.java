package junit.org.rapidpm.microservice.rest.v002;

import static junit.org.rapidpm.microservice.rest.v002.ServiceDemoRestInfo.PATH_BASE;
import static junit.org.rapidpm.microservice.rest.v002.ServiceDemoRestInfo.PATH_METHODE_DOWORK;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 */
@Path(PATH_BASE)
public interface ServiceDemoRest extends ServiceDemo {

  @Override
  @GET()
  @Produces("text/plain")
  @Path(PATH_METHODE_DOWORK)
  String doWork();

}

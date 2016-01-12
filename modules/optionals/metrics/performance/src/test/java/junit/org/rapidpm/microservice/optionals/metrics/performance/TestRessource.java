package junit.org.rapidpm.microservice.optionals.metrics.performance;

import org.rapidpm.proxybuilder.staticgenerated.annotations.StaticMetricsProxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by svenruppert on 02.01.16.
 */
@Path("/OverviewTest")
@StaticMetricsProxy
public class TestRessource {
  @GET()
  public String doWork() {
    final LocalDateTime now = LocalDateTime.now();
    final String format = now.format(DateTimeFormatter.ISO_DATE_TIME);
    return format;
  }
}

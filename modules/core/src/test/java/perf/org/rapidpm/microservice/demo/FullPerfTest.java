package perf.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.jboss.resteasy.test.TestPortProvider;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.rapidpm.microservice.Main;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.TimeUnit;

/**
 * Created by svenruppert on 10.06.15.
 */

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, warmups = 2)
@Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(value = 4)
@State(Scope.Thread)
public class FullPerfTest {


  public FullPerfTest() {
    System.setProperty(Main.RESTEASY_PORT, Main.PORT_REST + "");
  }

  @Benchmark
  public void testApplicationPath(Blackhole blackhole, BenchmarkState benchmarkState, BenchmarkStateThread benchmarkStateThread) {

//    Assert.assertNotNull(benchmarkState);
    String val = benchmarkStateThread.webTarget
        .request()
        .get(String.class);
//    Assert.assertEquals("Hello Rest World CDI Service", val);
    blackhole.consume(val);
  }


  @Benchmark
  public void benchmarkRuntimeOverhead() {
  }


  @State(Scope.Benchmark) // einmal pro Testdurchlauf
  public static class BenchmarkState {

    public BenchmarkState() {
      System.out.println("constructor " + BenchmarkState.class.getSimpleName());
    }

    @Setup
    public void setUp() throws Exception {
      System.out.println("BenchmarkState-setUp = " + System.nanoTime());

      Main.deploy();
    }

    @TearDown
    public void tearDown() {
      System.out.println("BenchmarkState-tearDown = " + System.nanoTime());
      Main.stop();
    }

  }

  @State(Scope.Thread)//einmal pro thread im TestDruchlauf
  public static class BenchmarkStateThread {
    public final String restAppPath = Main.CONTEXT_PATH_REST;
    public final String ressourcePath = Resource.class.getAnnotation(Path.class).value();
    public final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    public final Client client = ClientBuilder.newClient();
    public WebTarget webTarget;

    public BenchmarkStateThread() {
      System.out.println("constructor " + BenchmarkStateThread.class.getSimpleName());
    }

    @Setup
    public void setUp() throws Exception {
      System.out.println("BenchmarkStateThread-setUp = " + System.nanoTime());
      System.out.println("generateURL = " + generateURL);
      webTarget = client.target(generateURL);
    }

    @TearDown
    public void tearDown() {
      System.out.println("BenchmarkStateThread-tearDown = " + System.nanoTime());
      client.close();
    }
  }
}

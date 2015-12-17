package perf.org.rapidpm.microservice.optionals.metrics.performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.rapidpm.microservice.Main
import org.rapidpm.microservice.Main.{deploy, stop}
import org.rapidpm.microservice.optionals.metrics.performance.Overview

class Rest_Overview extends Simulation {

  val httpConf = http
    .baseURL("http://" + Main.DEFAULT_HOST + ":" + Main.DEFAULT_REST_PORT + "/") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
  val scn = scenario("Overview Test")
    .repeat(1000) {
      exec(http("request_0001").get(baseURL + Overview.LIST_ALL_HISTOGRAMMS))
//      exec(http("request_0002").get(baseURL + Overview.LIST_ALL_HISTOGRAMMS))
    }
  private val baseURL: String = "rest/metrics/performance/overview/"


  //  setUp(scn.inject(atOnceUsers(100)).protocols(httpConf))
  setUp(scn.inject(atOnceUsers(100)).protocols(httpConf))


  before {
    println("Simulation is about to start!")
    println("Start MicroService")
    deploy()
    println("MicroService Started")
  }

  after {
    println("Stop MicroService")
    stop()
    println("Simulation is finished!")
  }

}
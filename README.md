
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.rapidpm.microservice/rapidpm-microservice/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.rapidpm.microservice/rapidpm-microservice)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/897f1488574d4780a905e2531e402e03)](https://www.codacy.com/app/sven-ruppert/rapidpm-microservice?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JavaMicroService/rapidpm-microservice&amp;utm_campaign=Badge_Grade)

# RapidPM - Microservice
A base implementation for a microservice based on the Undertow.

The Core Service will listen on IP 0.0.0.0
The base configuration will start Servlets at port 7080 and REST-Endpoints at 7081.

## Release and JDK info
The releases 1.0.x are based on JDK8 the releases 1.1.x are based on JDK10.
Both are supported so far.


## Getting started
The use of the MicroService is fairly simple. Just call **Main.deploy()**. This will start the MicroService with
all servlets and REST endpoints it can find.   

### REST example
Let's create a simple service that will multiply two parameters. The service will listen on the path **/multiply** and returns 
 an Integer. Here is the code:

```java
@Path("/multiply")
public class Service {

  @GET()
  @Produces("text/plain")
  public int get(@QueryParam("x") int x, @QueryParam("y") int y) {
    return Math.multiplyExact(x, y);
  }
}
```
  
And the corresponding JUnit Test:

```java
public class RestTest {

  @Test
  public void testRestPath() throws Exception {
    Main.deploy();
    
    Client client = ClientBuilder.newClient();

    final String generateURL = TestPortProvider.generateURL("/rest" + "/multiply");
    System.out.println("generateURL = " + generateURL);
    int val = client
        .target(generateURL)
        .queryParam("x", 2)
        .queryParam("y", 21)
        .request()
        .get(Integer.class);
    Assert.assertEquals(42, val); // the only truth
    
    client.close();
    Main.stop();
  }

}
```

### Servlet example
Here is a simple WebServlet, that will be deployed by the MicroService as soon as **Main.deploy()** is called:

```java
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter writer = resp.getWriter();
    writer.write("hello " + req.getParameter("name"));
    writer.close();
  }

  @Override
  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
```

And the corresponding JUnit Test:

```java
public class ServletTest extends BaseTest{
  private static String url;

  @Before
  public void setUp() throws Exception {
    Main.deploy();
    url = "http://127.0.0.1:" + System.getProperty(MainUndertow.SERVLET_PORT_PROPERTY)
        + MainUndertow.MYAPP
        + HelloServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
    System.out.println("generateURL = " + url);
  }
  
  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testServletGetRequest() throws Exception {
    Client client = ClientBuilder.newClient();

    Response response = client
        .target(url)
        .queryParam("name", "marvin")
        .request()
        .get();
    Assert.assertEquals("hello marvin", response.readEntity(String.class));
    response.close();
    client.close();
  }
}
```
## Examples

See [rapidpm-microservice-examples](https://github.com/RapidPM/rapidpm-microservice-examples) for more demos

# Parameters

The MicroService supports by  default the following startup parameters:
* restPort
* restHost
* servletPort
* servletHost

For example, to set the servlet port use "-servletPort 7081".

Additionally, if you want to define your own parameters add the dependency **rapidpm-microservice-modules-optionals-cli** and use the interface *CmdLineStartupAction*:

```java
package org.rapidpm.microservice.optionals.cli;

public interface CmdLineStartupAction {
    List<Option> getOptions();
    void execute(CommandLine cmdLine);
}
```

Here's an example implementation that reads in a single property:
```java
public class MyPropertyCmdLineOption implements CmdLineStartupAction {

  public static final String OPT = "mp";

  @Override
  public List<Option> getOptions() {
    return Collections.singletonList(new Option(OPT, "myproperty", true, "set a value for this system property"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    String optionValue = commandLine.getOptionValue(OPT);
    if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
      System.setProperty("myproperty", optionValue);
    }
  }
}  
```

# Startup / Shutdown Actions

One great thing about the MicroService is that it gives you the ability
to define what should be happen before the MicroService starts and what should
be done after the MicroService stopped.  
To do this define a new action using the interface *MainStartupAction* or *MainShutdownAction* and
implement the method execute().
```java
@FunctionalInterface
public interface MainStartupAction {
  void execute(Optional<String[]> args);
}

@FunctionalInterface
public interface MainShutdownAction {
  void execute(Optional<String[]> args);
}
```
# Administration Module
The MicroService administration module makes it possible to conveniently shut down the service with an HTTP call. 
Once the module is included to your service, it will host another path **/admin/basicadministration/{timeout}** besides the servlet and rest paths. 
The path parameter behind the last **/** sets the milliseconds the service will wait till it stops.  
To include this function just add the artifact **rapidpm-microservice-modules-optionals-admin** as a dependency in your pom.
  
  
Example call to shutdown a MicroService:
```
http://127.0.0.1:7080/rest/admin/basicadministration/1000

Output:
[XNIO-2 task-1] WARN org.rapidpm.microservice.Main - shutdown delay [ms] = 1000
[Timer-0] WARN org.rapidpm.microservice.Main - delayed shutdown  now = 2016-11-22T19:33:59.159

```

# CommandLine Instructions
For an overview of the default commandline instructions pass the argument `-h` to the microservice jar. It will show you this:
```
 -h, -help:  Print this page
 -restPort:  Port for REST
 -restHost:  Host IP for REST
 -servletPort:  Port for optionslets
 -servletHost:  Host IP for optionslets
```

If you want to define your own parameters add the dependency **rapidpm-microservice-modules-optionals-cli** and use the interface *CmdLineStartupAction*:

```java
package org.rapidpm.microservice.optionals.cli;

public interface CmdLineStartupAction {
    List<Option> getOptions();
    void execute(CommandLine cmdLine);
}
```

Here is an example implementation that reads in a single property:
```java
public class MyPropertyCmdLineOption implements CmdLineStartupAction {

  public static final String OPT = "mp";

  @Override
  public List<Option> getOptions() {
    return Collections.singletonList(new Option(OPT, "myproperty", true, "set a value for this system property"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    String optionValue = commandLine.getOptionValue(OPT);
    if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
      System.setProperty("myproperty", optionValue);
    }
  }
}  
```

# Metrics
This section describes the different metrics the MicroService has to offer.

## Active Resources
Normally the MicroService prints out all of it's hosted resources, e.g. servlet classes.
With the activeresources module you can enable the option to ask for all resources made available through urls by the MicroService.
To retrieve the active resources call the path **/info/activeurls**, it will return a JSON message as response.

Example call:
```
http://127.0.0.1:7081/rest/info/activeurls
```
Response:
```json
{
  "restUrls":["http://0.0.0.0:7081/rest/pojo","http://0.0.0.0:7081/rest/info/activeurls","http://0.0.0.0:7081/rest/test","http://0.0.0.0:7081/rest/OverviewTest/pathA - paramA"],
  "servletUrls":["http://0.0.0.0:7080/microservice/test","http://0.0.0.0:7080/microservicetestServlet"],
  "singletonUrls":[],
  "servletCounter":2}
```

## Health
The MicroService is based on the highly lightweight Undertow server. To retrieve information about the actual health of
the underlying server this module gives you access to the data of the Undertow SessionManager. 

Example call:
```
http://127.0.0.1:7081/rest/metrics/health
```

Response:
```json
[
  {
    "transientSessions": [],
    "deploymentName": "ROOT.war",
    "allSessions": [],
    "activeSessions": [],
    "startTime": 1479842722116,
    "maxActiveSessions": -1,
    "activeSessionCount": 0,
    "averageSessionAliveTime": 0,
    "createdSessionCount": 0,
    "expiredSessionCount": 0,
    "maxSessionAliveTime": 0,
    "rejectedSessions": 0
  }
]
```
## JVM 
Additionally to the core health of the Undertow Server, this module gives you an inside into the JVM it's running in.
The information about the JVM splits into four sections, resulting in four paths (starting with **/metics/jvm**) you can call:
  
* **loadinfos**: GarbageCollector  
* **osinfos**: informations about the os it's running on  
* **specinfos**: RuntimeMXBean with system properties of the JVM    
* **memoryinfos**: memory usage  

### loadinfos
Content: 

- collectionCount
- collectionTime
- memoryPoolNames
- name


Example call:

    http://127.0.0.1:7081/rest/metrics/jvm/loadinfos

Response:
```json
[
  {
    "collectionCount":8,
    "collectionTime":79,
    "memoryPoolNames":["PS Eden Space","PS Survivor Space"],
    "name":"PS Scavenge"
  },
  {
    "collectionCount":0,
    "collectionTime":0,
    "memoryPoolNames":["PS Eden Space","PS Survivor Space","PS Old Gen"],
    "name":"PS MarkSweep"
  }
 ]
```

### osinfos
Content: 

- name
- arch
- version
- processors


Example call:

    http://127.0.0.1:7081/rest/metrics/jvm/osinfos

Response:
```json
{
  "name":"Windows 10",
  "arch":"amd64",
  "version":"10.0",
  "processors":8
}
```

### specinfos
Content: 

- name
- vendor
- version


Example call:

    http://127.0.0.1:7081/rest/metrics/jvm/specinfos
    
Response:
```json
{
  "name":"Java Virtual Machine Specification",
  "vendor":"Oracle Corporation",
  "version":"1.8"
}
```

### memoryinfos
Content: 

- freeMemory
- maxMemory
- totalMemory
- heapMemoryUsage
- nonHeapMemoryUsage
- objectPendingFinalizationCount


Example call:

    http://127.0.0.1:7081/rest/metrics/jvm/memoryinfos

Response:
```json
{
  "freeMem":196903200,
  "maxMem":3808428032,
  "totalMem":743440384,
  "objectPendingFinalizationCount":0,
  "heapMemoryUsage":{"init":268435456,"used":546537184,"committed":743440384,"max":3808428032},
  "nonHeapMemoryUsage":{"init":2555904,"used":26764928,"committed":27852800,"max":-1}
}
```

## Performance
This section is all about performance metrics and the way the MicroService can give you information about the performance of your application. 
If you include this module as dependency, the MicroService will add a few URLs that give you accessibility to the metrics of your classes and methods.
Here's an overview of the paths:
```
http://127.0.0.1:7081/rest/metrics/performance/reporter/stopJMXReporter
http://127.0.0.1:7081/rest/metrics/performance/reporter/startJMXReporter
http://127.0.0.1:7081/rest/metrics/performance/reporter/startConsoleReporter
http://127.0.0.1:7081/rest/metrics/performance/reporter/stopConsoleReporter
http://127.0.0.1:7081/rest/metrics/performance/histogramms/activateMetricsForClass - classFQN
http://127.0.0.1:7081/rest/metrics/performance/histogramms/deActivateMetricsForClass - classFQN
http://127.0.0.1:7081/rest/metrics/performance/histogramms/activateMetricsForPKG - pkgFQN
http://127.0.0.1:7081/rest/metrics/performance/histogramms/deActivateMetricsForPKG - pkgFQN
http://127.0.0.1:7081/rest/metrics/performance/histogramms/listOneHistogramm - histogrammName
http://127.0.0.1:7081/rest/metrics/performance/histogramms/removeOneHistogramm - histogrammName
http://127.0.0.1:7081/rest/metrics/performance/histogramms/listAllActivateMetrics
http://127.0.0.1:7081/rest/metrics/performance/histogramms/listAllHistogramms
http://127.0.0.1:7081/rest/metrics/performance/histogramms/listAllHistogrammNames
```

There are different ways to activate metrics:

* **Runtime**: activate metrics for single classes with the path **/histogramms/activateMetricsForClass/** and add the name of the desired
class as query parameter
* **Runtime**: activate packages with the path **/histogramms/activateMetricsForPKG/** and add the name of the target package as query parameter
* **DDI framework**:
    * add the annotation *@StaticMetricsProxy* to your class or method
    * activate metrics with the method *DI.activateMetrics(Class clazz)*
 
To retrieve the information use one of *list* calls or activate one of the reporter. The **ConsoleReporter** will continually print out
to the console, while the **JMXReporter** opens an interface for a Java Management Extensions, e.g. the jconsole.

# ServiceWrapper
Born out of the need to start and stop the MicroService as a service on Windows, the ServiceWrapper (combined with Apache's prunsrv) makes this possible.
But we can't stop the MicroService without closing the terminal or the need to stop it with the [shutdown call](admin.md) inside a Windows service. Another way is necessary to stop it.
Basically the ServiceWrapper works as a proxy for the start & stop calls between the MicroService and the Windows service.
Here is a step-by-step guide to create a service for a MicroService on Windows:
Requirements: 

* prunsrv.exe by [Apache Commons](http://www.apache.org/dist/commons/daemon/binaries/windows/) 
* path to the jvm.dll, based in the Java installation directory
* (Optional) if no port is given, the MicroService will use the default port 7080, otherwise provide a property file with rest.port=...
* (Optional) if you are using log4j provide a log4j2 config file
  
---    
   
```
set SERVICENAME=MyService
set DISPLAYNAME=MyService
set JVMPARAMS=--Jvm "C:/Program Files/Java/jre1.8.0_112/bin/server/jvm.dll" --JvmMs 128 --JvmMx 1024 ++JvmOptions=-Dlog4j.configurationFile="file://C:/log4j2.xml"
set CLASSPATH="C:/myservice.jar"
set MAINCLASS=org.rapidpm.microservice.optionals.service.ServiceWrapper
set STARTPARAMS=-i#"C:/myservice.properties"

C:/prunsrv //IS//%SERVICENAME% --DisplayName="%DISPLAYNAME%" --Startup=manual --Classpath=%CLASSPATH% --StartMode=jvm --StartClass=%MAINCLASS% ++StartParams=%STARTPARAMS% --StopMode=jvm --StopClass=%MAINCLASS% --StopParams=SHUTDOWN --StopTimeout 5 

pause
```
1. Create an install script, e.g. install_myservice.bat above
2. Run the bat file
3. Now Windows should open a popup and asks for permission to install the new service. After you done this the service should
be listed in the services.msc.
4. Once the service is started it will create a file named microservice.rest. This file is used to store the actual port of the service.
5. If you stop the service, the ServiceWrapper will call the [shutdown url](admin.md). 
  
  
To remove the service create another script, e.g. remove_myservice.bat:
```
@echo off

C:/example/prunsrv //DS//MyService

pause
```




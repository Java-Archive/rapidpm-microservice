package junit.org.rapidpm.microservice.propertyservice.rest.v003;


import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.*;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;
import org.rapidpm.microservice.propertyservice.persistence.file.PropertiesFileLoader;
import org.rapidpm.microservice.propertyservice.rest.PropertyServiceRest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;

public class RestTest003 extends BasicRestTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(new PropertiesFileLoader());
    DI.registerClassForScope(PropertyServiceImpl.class, JVMSingletonInjectionScope.class.getSimpleName());
    System.setProperty("mapname", RestTest003.class.getSimpleName());
    //System.setProperty("file", this.getClass().getResource("").getPath());
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test @Ignore
  public void test001() throws Exception {
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getConfigurationFile?filename=test.xml";
    Client client = ClientBuilder.newClient();
    final File response = client
        .target(uri)
        .request()
        .get(File.class);
    System.out.println("response = " + response.getName());

  }



}




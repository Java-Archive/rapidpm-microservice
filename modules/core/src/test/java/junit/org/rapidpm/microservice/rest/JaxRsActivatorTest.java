package junit.org.rapidpm.microservice.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.PingMe;
import junit.org.rapidpm.microservice.rest.provider.ProviderImpl;

public class JaxRsActivatorTest {
  private JaxRsActivator jaxRsActivator = new JaxRsActivator();

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    final Class<?> aClass = ProviderImpl.class;
    DI.activatePackages(aClass);
    DI.activatePackages(InterfaceResource.class);
  }

  @Test
  public void testGetClasses() {
    Set<Class<?>> classes = jaxRsActivator.getClasses();
    assertThat(classes.contains(PingMe.class), is(true));
    assertThat(classes.contains(ProviderImpl.class), is(true));
    assertThat(classes.contains(InterfaceResource.class), is(false));
  }

  @Test
  public void testGetPathResources() {
    Set<Class<?>> classes = jaxRsActivator.getPathResources();
    assertThat(classes.contains(PingMe.class), is(true));
    assertThat(classes.contains(ProviderImpl.class), is(false));
    assertThat(classes.contains(InterfaceResource.class), is(true));
  }

  @Test
  public void testGetInterfacesWithPathAnnotation() {
    Set<Class<?>> classes = jaxRsActivator.getInterfacesWithPathAnnotation();
    assertThat(classes.contains(PingMe.class), is(false));
    assertThat(classes.contains(ProviderImpl.class), is(false));
    assertThat(classes.contains(InterfaceResource.class), is(true));

  }

  @Path("/interfaceResource")
  public static interface InterfaceResource {
    @GET
    public String hello();
  }

  public static class InterfaceResourceImpl implements InterfaceResource {
    @Override
    public String hello() {
      return "Hello, World!";
    }
  }
}

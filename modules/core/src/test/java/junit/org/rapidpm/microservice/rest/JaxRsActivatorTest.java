package junit.org.rapidpm.microservice.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.Set;
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
  }

  @Test
  public void testGetClasses() {

    Set<Class<?>> classes = jaxRsActivator.getClasses();
    assertThat(classes.contains(PingMe.class), is(true));
    assertThat(classes.contains(ProviderImpl.class), is(true));
  }

}

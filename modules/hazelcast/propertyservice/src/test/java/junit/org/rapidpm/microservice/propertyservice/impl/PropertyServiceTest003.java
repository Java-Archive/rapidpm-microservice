package junit.org.rapidpm.microservice.propertyservice.impl;

import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.impl.PropertyService;

import javax.inject.Inject;
import java.util.Set;

public class PropertyServiceTest003 extends BaseDITest {

  @Inject
  PropertyService service01;
  @Inject
  PropertyService service02;

  @Before
  public void setUp() throws Exception {
    DI.activateDI(this);
  }

  @Test
  public void test001() throws Exception {
    service01.init(this.getClass().getResource("example.properties").getPath());
    service02.init(null);
    final String singleProperty = service02.getSingleProperty("example.part01.001");
    Assert.assertNotNull(singleProperty);
    Assert.assertFalse(singleProperty.isEmpty());
    Assert.assertEquals("test001", singleProperty);
  }

  @Test
  public void test002() throws Exception {
    service01.init(null);
    service02.init(this.getClass().getResource("example.properties").getPath());

    final String singleProperty = service01.getSingleProperty("example.part01.001");
    Assert.assertNotNull(singleProperty);
    Assert.assertFalse(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    service01.init(null);
    service02.init(this.getClass().getResource("example.properties").getPath());

    final Set<String> indexToDomain = service01.getIndexToDomain("example");
    Assert.assertNotNull(indexToDomain);
    Assert.assertTrue(indexToDomain.size() == 2);
  }


}

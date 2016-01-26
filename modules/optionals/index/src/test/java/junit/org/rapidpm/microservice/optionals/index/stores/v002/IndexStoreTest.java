package junit.org.rapidpm.microservice.optionals.index.stores.v002;

import junit.org.rapidpm.microservice.optionals.index.stores.IndexStoreBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfType;

import java.util.List;
import java.util.Set;

/**
 * Created by Sven Ruppert on 18.01.16.
 */
public class IndexStoreTest extends IndexStoreBaseTest {


  public static final String TESTINDEX = IndexStoreTest.class.getSimpleName();


  @Test
  public void indexElement() throws Exception {
    Assert.assertTrue(indexStore.containsIndex(TESTINDEX));
    Assert.assertFalse(indexStore.containsIndex("NOOP"));

    final Set<String> indexNameSet = indexStore.getIndexNameSet();
    Assert.assertNotNull(indexNameSet);
    Assert.assertFalse(indexNameSet.isEmpty());
//    Assert.assertEquals(1, indexNameSet.size());

    final IndexOfType<String> index = indexStore.getIndex(TESTINDEX);
    Assert.assertNotNull(index);

    index.addElement("Hallo Nase Trollpopel");
    final List<String> hallo = index.query("Trollpopel");
    Assert.assertNotNull(hallo);

    Assert.assertFalse(hallo.isEmpty());
  }

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }
}
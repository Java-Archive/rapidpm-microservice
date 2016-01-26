package junit.org.rapidpm.microservice.optionals.index.stores.v001;

import junit.org.rapidpm.microservice.optionals.index.stores.IndexStoreBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import java.io.File;

/**
 * Created by Sven Ruppert on 26.01.16.
 */
public class IndexStoreRemoveTest extends IndexStoreBaseTest {

  public static final String TESTINDEX = IndexStoreRemoveTest.class.getSimpleName();

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }

  @Test
  public void testRemoveIndex001() throws Exception {
    Assert.assertTrue(indexStore.containsIndex(TESTINDEX));
    final boolean b = indexStore.removeIndex(TESTINDEX);
    Assert.assertTrue(b);

    final File baseIndexDir = new File(IndexManagement.IDX_BASE_DIR);
    final File targetDir = new File(baseIndexDir, TESTINDEX);

    Assert.assertFalse(indexStore.containsIndex(TESTINDEX));

    Assert.assertFalse(targetDir.exists());


  }
}
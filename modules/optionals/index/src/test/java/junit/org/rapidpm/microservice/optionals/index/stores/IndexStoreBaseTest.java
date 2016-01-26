package junit.org.rapidpm.microservice.optionals.index.stores;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfTypeString;

import javax.inject.Inject;

/**
 * Created by Sven Ruppert on 26.01.16.
 */
public abstract class IndexStoreBaseTest {

  @Inject public IndexStore indexStore;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    DI.registerClassForScope(IndexStore.class, JVMSingletonInjectionScope.class.getSimpleName());
//    DI.registerClassForScope(DefaultIndexStore.class, JVMSingletonInjectionScope.class.getSimpleName());
    DI.activateDI(this);


    final IndexOfTypeString indexOfTypeString = IndexOfTypeString.newBuilder()
        .withIndexName(getTestIndexName())
        .build();

    indexStore.removeIndex(getTestIndexName());
    indexStore.addIndexOfType(getTestIndexName(), indexOfTypeString);
  }

  public abstract String getTestIndexName();

  @Test
  public void name() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
    indexStore.removeIndex(getTestIndexName());
    DI.clearReflectionModel();
  }
}

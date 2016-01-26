package junit.org.rapidpm.microservice.optionals.index;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Before;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.Main.MainStartupAction;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfTypeString;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Sven Ruppert on 26.01.16.
 */
public abstract class IndexBasicRestTest extends BasicRestTest {

  @Inject public IndexStore indexStore;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    DI.activateDI(this);

    final IndexOfTypeString indexOfTypeString = IndexOfTypeString.newBuilder()
        .withIndexName(getTestIndexName())
        .build();

    indexStore.removeIndex(getTestIndexName());
    indexStore.addIndexOfType(getTestIndexName(), indexOfTypeString);
  }

  public abstract String getTestIndexName();


  public static class RegisterDefaultIndexStoreAsSingleton implements MainStartupAction {

    public RegisterDefaultIndexStoreAsSingleton() {
      System.out.println("RegisterDefaultIndexStoreAsSingleton inner . = ");
    }

    @Override
    public void execute(final Optional<String[]> args) {
      DI.registerClassForScope(IndexStore.class, JVMSingletonInjectionScope.class.getSimpleName());
    }
  }

}

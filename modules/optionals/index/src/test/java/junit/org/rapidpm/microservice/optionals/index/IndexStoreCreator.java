package junit.org.rapidpm.microservice.optionals.index;

import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.optionals.index.stores.DefaultIndexStore;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;

/**
 * Created by Sven Ruppert on 26.01.16.
 */
@Produces(IndexStore.class)
public class IndexStoreCreator implements Producer<IndexStore> {
  @Override
  public IndexStore create() {
    return new DefaultIndexStore();
  }
}

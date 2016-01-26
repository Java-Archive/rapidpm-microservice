package org.rapidpm.microservice.optionals.index.stores;

import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.Main.MainStartupAction;

import java.util.Optional;

/**
 * Created by Sven Ruppert on 25.01.16.
 */
public class RegisterDefaultIndexStoreAsSingleton implements MainStartupAction {

  public RegisterDefaultIndexStoreAsSingleton() {
    System.out.println("RegisterDefaultIndexStoreAsSingleton = ");
  }

  @Override
  public void execute(final Optional<String[]> args) {
    DI.registerClassForScope(IndexStore.class, JVMSingletonInjectionScope.class.getSimpleName());
//    DI.registerClassForScope(DefaultIndexStore.class, JVMSingletonInjectionScope.class.getSimpleName());
  }
}

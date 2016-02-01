/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.optionals.index.stores;

import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.Main.MainStartupAction;

import java.util.Optional;

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

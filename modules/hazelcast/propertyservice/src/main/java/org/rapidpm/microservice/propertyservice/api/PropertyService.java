/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.propertyservice.api;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public interface PropertyService {
  void init(@Nullable String source);

  void initFromCmd();

  String loadProperties(String scope);

  String getSingleProperty(String property);

  Set<String> getIndexOfLoadedProperties();

  Set<String> getIndexOfScope(String scope);

  Map<String, String> getPropertiesOfScope(String scope);

  boolean isRunning();

  void forget();

  void shutdown();

  File getConfigurationFile(String filename) throws IOException;
}

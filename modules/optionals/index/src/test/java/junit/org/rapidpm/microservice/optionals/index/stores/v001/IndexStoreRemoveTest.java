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

package junit.org.rapidpm.microservice.optionals.index.stores.v001;

import junit.org.rapidpm.microservice.optionals.index.stores.IndexStoreBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import java.io.File;

public class IndexStoreRemoveTest extends IndexStoreBaseTest {

  public static final String TESTINDEX = IndexStoreRemoveTest.class.getSimpleName();

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }

  @Test
  public void testRemoveIndex001() throws Exception {
    Assertions.assertTrue(indexStore.containsIndex(TESTINDEX));
    final boolean b = indexStore.removeIndex(TESTINDEX);
    Assertions.assertTrue(b);

    final File baseIndexDir = new File(IndexManagement.IDX_BASE_DIR);
    final File targetDir = new File(baseIndexDir, TESTINDEX);

    Assertions.assertFalse(indexStore.containsIndex(TESTINDEX));

    Assertions.assertFalse(targetDir.exists());


  }
}
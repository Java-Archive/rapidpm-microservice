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
package junit.org.rapidpm.microservice.optionals.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest006 extends BaseCmdlineTest {

    @Test
    public void test001() throws Throwable {
        String[] args = {"-h"};
        int status = startAndGetExit(args);
        int expectedStatus = 0;
        Assertions.assertEquals( expectedStatus, status);
    }

    @Test
    public void test002() throws Throwable {
        final String[] args = {"-trololo", "-jolo"};
        final int expectedStatus = 1;
        final int status = startAndGetExit(args);
        Assertions.assertEquals( expectedStatus, status);
    }
}

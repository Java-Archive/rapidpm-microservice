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

package junit.org.rapidpm.microservice.rest.optionals.fs.memory;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public class MemoryFileSystemProviderTest {


  @Test
  public void test001() throws Exception {

    final Path foo;
    Configuration configuration = Configuration.unix().toBuilder()
        .setAttributeViews("basic", "owner", "posix", "unix")
        .setWorkingDirectory("/home/user")
        .build();

    try (final FileSystem fs = Jimfs.newFileSystem(configuration)) {


      final Set<String> supportedFileAttributeViews = fs.supportedFileAttributeViews();
      System.out.println("supportedFileAttributeViews = " + supportedFileAttributeViews);

      foo = fs.getPath("/foo");
      Files.createDirectory(foo);

      final Path hello = foo.resolve("hello.txt");


      final Set<PosixFilePermission> perms = new HashSet<>();
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_WRITE);
//      perms.add(PosixFilePermission.OWNER_EXECUTE);
      //add group permissions
      perms.add(PosixFilePermission.GROUP_READ);
      perms.add(PosixFilePermission.GROUP_WRITE);
//      perms.add(PosixFilePermission.GROUP_EXECUTE);
      //add others permissions
      perms.add(PosixFilePermission.OTHERS_READ);
      perms.add(PosixFilePermission.OTHERS_WRITE);
//      perms.add(PosixFilePermission.OTHERS_EXECUTE);
//      final FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(perms);
//      final Path file1 = Files.createFile(hello, setFileAttribute);
      Files.write(hello, ImmutableList.of("hello world"), StandardCharsets.UTF_8);

      Files.setPosixFilePermissions(hello, perms);


      Files.list(foo)
          .forEach(file -> {
            try {
              System.out.println(String.format("%s (%db) - rights (%s)",
                  file,
                  Files.readAllBytes(file).length,
                  Files.getPosixFilePermissions(file)));
            } catch (Exception e) {
              e.printStackTrace();
            }
          });


    }
  }

}
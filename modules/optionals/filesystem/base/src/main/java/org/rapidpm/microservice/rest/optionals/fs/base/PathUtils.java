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

package org.rapidpm.microservice.rest.optionals.fs.base;

import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public class PathUtils {

  private PathUtils() {
  }

  public static Set<PosixFilePermission> createUrwxGrwxArwx() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    perms.add(PosixFilePermission.OWNER_EXECUTE);
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    perms.add(PosixFilePermission.GROUP_EXECUTE);
    //add others permissions
    perms.add(PosixFilePermission.OTHERS_READ);
    perms.add(PosixFilePermission.OTHERS_WRITE);
    perms.add(PosixFilePermission.OTHERS_EXECUTE);
    return perms;
  }

  public static Set<PosixFilePermission> createUrwxGoooAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    perms.add(PosixFilePermission.OWNER_EXECUTE);
    //add group permissions
    //add others permissions
    return perms;
  }

  public static Set<PosixFilePermission> createUoooGrwxAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    perms.add(PosixFilePermission.GROUP_EXECUTE);
    //add others permissions
    return perms;
  }

  public static Set<PosixFilePermission> createUoooGoooArwx() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    //add group permissions
    //add others permissions
    perms.add(PosixFilePermission.OTHERS_READ);
    perms.add(PosixFilePermission.OTHERS_WRITE);
    perms.add(PosixFilePermission.OTHERS_EXECUTE);
    return perms;
  }

  public static Set<PosixFilePermission> createUrwoGoooAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    //add group permissions
    //add others permissions
    return perms;
  }


  public static Set<PosixFilePermission> createUrwoGrwoAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    //add others permissions
    return perms;
  }

  public static Set<PosixFilePermission> createUrwoGrwoArwo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    //add others permissions
    perms.add(PosixFilePermission.OTHERS_READ);
    perms.add(PosixFilePermission.OTHERS_WRITE);
    return perms;
  }

  public static Set<PosixFilePermission> createUrooGoooAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    //add group permissions
    //add others permissions
    return perms;
  }


  public static Set<PosixFilePermission> createUrooGrooAooo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    //add others permissions
    return perms;
  }

  public static Set<PosixFilePermission> createUrooGrooAroo() {
    final Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    //add group permissions
    perms.add(PosixFilePermission.GROUP_READ);
    //add others permissions
    perms.add(PosixFilePermission.OTHERS_READ);
    return perms;
  }


}

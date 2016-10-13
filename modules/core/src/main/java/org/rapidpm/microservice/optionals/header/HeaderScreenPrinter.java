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

package org.rapidpm.microservice.optionals.header;

import org.rapidpm.ddi.DI;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class HeaderScreenPrinter {


  public void printOnScreen() {

    final Set<Class<? extends HeaderInfo>> typesAnnotatedWith = DI.getSubTypesOf(HeaderInfo.class);
    if (typesAnnotatedWith.isEmpty()) {
      String txt =
          "   ___            _    _____  __  ___        __  ____                              _        \n" +
              "  / _ \\___ ____  (_)__/ / _ \\/  |/  / ____  /  |/  (_)__________  ______ _____  __(_)______ \n" +
              " / , _/ _ `/ _ \\/ / _  / ___/ /|_/ / /___/ / /|_/ / / __/ __/ _ \\(_-< -_) __/ |/ / / __/ -_)\n" +
              "/_/|_|\\_,_/ .__/_/\\_,_/_/  /_/  /_/       /_/  /_/_/\\__/_/  \\___/___|__/_/  |___/_/\\__/\\__/ \n" +
              "         /_/                                                                                \n";
      System.out.println(txt);
      System.out.println("");
      System.out.println(" this project is hosted at https://github.com/orgs/JavaMicroService/dashboard");
      System.out.println(" examples you can find at  https://github.com/orgs/JavaMicroService/rapidpm-microservice-examples");
      System.out.println(" if you have question      mailto:sven.ruppert@gmail.com or Twitter: @SvenRuppert");
      System.out.println("");
    } else {
      typesAnnotatedWith
          .stream()
          .filter(c -> c.isAnnotationPresent(Header.class))
          .sorted(Comparator.comparingInt(c2 -> c2.getAnnotation(Header.class).order()))
          .map((Function<Class<? extends HeaderInfo>, HeaderInfo>) DI::activateDI)
          .filter(Objects::nonNull)
          .map(HeaderInfo::createHeaderInfo)
          .forEach(System.out::println);
    }
  }
}

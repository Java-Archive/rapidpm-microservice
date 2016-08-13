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

package org.rapidpm.microservice;

import org.rapidpm.ddi.DI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static final Timer TIMER = new Timer(true);
  public static final String DEFAULT_HOST = "0.0.0.0";

  private static Optional<String[]> cliArguments;

  private Main() {
  }

  public static void main(String[] args) {
    cliArguments = Optional.ofNullable(args);
    deploy(cliArguments);
  }

  public static void deploy() {
    deploy(Optional.empty());
  }

  public static void deploy(Optional<String[]> args) {
    cliArguments = args;
    executeStartupActions(args);
    DI.bootstrap(); // per config steuern
    MainUndertow.deploy(args); // TODO make it non-static
  }

  private static void executeStartupActions(final Optional<String[]> args) {
    final Set<Class<? extends MainStartupAction>> classes = DI.getSubTypesOf(MainStartupAction.class);
    createInstances(classes)
        .stream()
        .map(DI::activateDI)
        .forEach((mainStartupAction) -> mainStartupAction.execute(args));
  }

  public static void stop(long delayMS) {
    LOGGER.warn("shutdown delay [ms] = " + delayMS);

    TIMER.schedule(new TimerTask() {
      @Override
      public void run() {
        LOGGER.warn("delayed shutdown  now = " + LocalDateTime.now());
        stop();
      }
    }, delayMS);
  }

  public static void stop() {
    executeShutdownActions(cliArguments);
    MainUndertow.stop(); // TODO make it non-static
  }

  private static void executeShutdownActions(Optional<String[]> args) {
    final Set<Class<? extends MainShutdownAction>> classes = DI.getSubTypesOf(MainShutdownAction.class);
    createInstances(classes)
        .stream()
        .map(DI::activateDI)
        .forEach((mainShutdownAction) -> mainShutdownAction.execute(args));
  }


  public static <T> List<T> createInstances(final Set<Class<? extends T>> classes) {
    return classes
        .stream()
        .map(c -> {
          try {
            return Optional.of(c.newInstance());
          } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("failed to create new instance ", e);
          }
          return Optional.<T>empty();
        })
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.<T>toList());
  }

  @FunctionalInterface
  public interface MainStartupAction {
    void execute(Optional<String[]> args);
  }

  @FunctionalInterface
  public interface MainShutdownAction {
    void execute(Optional<String[]> args);
  }
}
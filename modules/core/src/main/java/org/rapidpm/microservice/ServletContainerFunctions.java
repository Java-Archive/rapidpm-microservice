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
package org.rapidpm.microservice;

import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.INCLUDE;
import static javax.servlet.DispatcherType.REQUEST;

import java.util.Collections;
import java.util.function.Function;

import javax.servlet.ServletContainerInitializer;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.rapidpm.frp.functions.TriFunction;
import org.stagemonitor.web.servlet.initializer.MainStagemonitorServletContainerInitializer;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletContainerInitializerInfo;
import io.undertow.servlet.util.ImmediateInstanceFactory;

/**
 *
 */
public interface ServletContainerFunctions {

  static TriFunction<DeploymentInfo, String, String, DeploymentInfo> addShiroFilter() {
    return (deploymentInfo , shiroFilterName , shiroShiroFilterMappin) -> deploymentInfo.addListener(new ListenerInfo(EnvironmentLoaderListener.class))
                                                                                        .addFilter(new FilterInfo(shiroFilterName , ShiroFilter.class))
                                                                                        .addFilterUrlMapping(shiroFilterName , shiroShiroFilterMappin , REQUEST)
                                                                                        .addFilterUrlMapping(shiroFilterName , shiroShiroFilterMappin , FORWARD)
                                                                                        .addFilterUrlMapping(shiroFilterName , shiroShiroFilterMappin , INCLUDE)
                                                                                        .addFilterUrlMapping(shiroFilterName , shiroShiroFilterMappin , ERROR);
  }

  static Function<DeploymentInfo, DeploymentInfo> addStagemonitor() {
    return (deploymentInfo) -> deploymentInfo.addServletContainerInitalizer(
        new ServletContainerInitializerInfo(
            MainStagemonitorServletContainerInitializer.class ,
            new ImmediateInstanceFactory<ServletContainerInitializer>(new MainStagemonitorServletContainerInitializer()) ,
            Collections.emptySet()
        ));
  }

}

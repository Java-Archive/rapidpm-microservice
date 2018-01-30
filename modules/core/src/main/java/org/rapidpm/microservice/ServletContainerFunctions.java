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
public class ServletContainerFunctions {

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

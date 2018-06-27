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
package org.rapidpm.microservice.optionals.metrics.jvm;


import com.google.gson.Gson;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.GCInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.MemoryInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.OSInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.SpecInfos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.management.*;

@Path("/metrics/jvm")
public class JVMRessources {


  @GET
  @Path("loadinfos")
  @Produces(MediaType.APPLICATION_JSON)
  public String loadInfos() {
    final OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
    final double systemLoadAverage = osMXBean.getSystemLoadAverage();

    final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    final String specName = runtimeMXBean.getSpecName();
    final String specVendor = runtimeMXBean.getSpecVendor();
    final String specVersion = runtimeMXBean.getSpecVersion();

    final GCInfos[] gcInfos = ManagementFactory.getGarbageCollectorMXBeans()
        .stream()
        .map(g -> new GCInfos()
                .collectionCount(g.getCollectionCount())
                .collectionTime(g.getCollectionTime())
                .name(g.getName())
//            .objectName(g.getObjectName().)
                .memoryPoolNames(g.getMemoryPoolNames())


        )
        .toArray(GCInfos[]::new);

//    Type listType = new TypeToken<ArrayList<GCInfos>>() {
//    }.getType();
    return new Gson().toJson(gcInfos, GCInfos[].class);
  }

  @GET
  @Path("osinfos")
  @Produces(MediaType.APPLICATION_JSON)
  public String osInfos() {
    final OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
    final String name = osMXBean.getName();
    final String arch = osMXBean.getArch();
    final String version = osMXBean.getVersion();
    final int processors = osMXBean.getAvailableProcessors();
    final OSInfos osInfos = new OSInfos()
        .arch(arch)
        .name(name)
        .processors(processors)
        .version(version);
    return new Gson().toJson(osInfos);

  }

  @GET
  @Path("specinfos")
  @Produces(MediaType.APPLICATION_JSON)
  public String specInfos() {
    final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    final String specName = runtimeMXBean.getSpecName();
    final String specVendor = runtimeMXBean.getSpecVendor();
    final String specVersion = runtimeMXBean.getSpecVersion();
    final SpecInfos specInfos = new SpecInfos().specName(specName).specVendor(specVendor).specVersion(specVersion);
    return new Gson().toJson(specInfos);

  }

  @GET
  @Path("memoryinfos")
  @Produces(MediaType.APPLICATION_JSON)
  public String memoryInfos() {
    final Runtime runtime = Runtime.getRuntime();
    final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    final MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
    final MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
    final int objectPendingFinalizationCount = memoryMXBean.getObjectPendingFinalizationCount();


    final MemoryInfos memoryInfos = new MemoryInfos()
        .freeMemory(runtime.freeMemory())
        .maxMemory(runtime.maxMemory())
        .totalMemory(runtime.totalMemory())
        .heapMemoryUsage(heapMemoryUsage)
        .nonHeapMemoryUsage(nonHeapMemoryUsage)
        .objectPendingFinalizationCount(objectPendingFinalizationCount);
    return new Gson().toJson(memoryInfos);
  }


}

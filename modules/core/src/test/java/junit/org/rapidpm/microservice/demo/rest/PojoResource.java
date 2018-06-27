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
package junit.org.rapidpm.microservice.demo.rest;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.demo.model.DataHolder;
import junit.org.rapidpm.microservice.demo.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/pojo")
public class PojoResource {


  //wird per Request erzeugt.
//  @Inject Service service;
  private final Service service = new Service();

  @GET()
  @Produces("text/plain")
  public String get() {
//    return  Arrays.asList("A", "B", service.doWork());
    final DataHolder dataHolder = new DataHolder();
    dataHolder.setTxtA("A");
    dataHolder.setTxtb("B");
    return new Gson().toJson(dataHolder);
  }
}

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

package junit.org.rapidpm.microservice.optionals.metrics.performance;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.optionals.metrics.performance.HistogrammSnapshot;
import org.rapidpm.microservice.optionals.metrics.performance.Histogramms;

import java.util.Set;

public class Histogramms002Test extends HistogrammBaseTest {


  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  public void test001() throws Exception {
    checkHistogramm(0);
    Assert.assertTrue(getActivatedMetrics().isEmpty());

    final String activateReq = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.ACTIVATE_METRICS_FOR_CLASS
        + "?" + Histogramms.QUERY_PARAM_CLASS_FQ_NAME + "=" + TestRessource.class.getName();

    Assert.assertEquals(Histogramms.OK, request(activateReq));

    checkHistogramm(runRequests());
    Assert.assertFalse(getActivatedMetrics().isEmpty());
    Assert.assertTrue(getActivatedMetrics().contains(TestRessource.class.getName()));


    //DeActivate Metrics
    final String deActivateReq = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.DE_ACTIVATE_METRICS_FOR_CLASS
        + "?" + Histogramms.QUERY_PARAM_CLASS_FQ_NAME + "=" + TestRessource.class.getName();

    Assert.assertEquals(Histogramms.OK, request(deActivateReq));

    checkHistogramm(runRequests()); // no Change counted
  }

  private void checkHistogramm(final int expected) {
    final String generateBasicReqURL = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.LIST_ONE_HISTOGRAMMS
        + "?" + Histogramms.QUERY_PARAM_HISTOGRAMM_NAME + "=" + HISTOGRAMM_NAME;

    final HistogrammSnapshot preHistogrammSnapshot = fromJsonWithCheck(
        requestWithCheck(
            generateBasicReqURL));

    Assert.assertEquals(expected, preHistogrammSnapshot.getHistogramCount());
  }

  private Set<String> getActivatedMetrics() {
    final String allActiveMetricsReq = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.LIST_ALL_ACTIVATED_METRICS;

    final String alleActiveMetrics001Result = request(allActiveMetricsReq);

    Assert.assertNotNull(alleActiveMetrics001Result);
    Assert.assertFalse(alleActiveMetrics001Result.isEmpty());
    final Set<String> activeMerticsSet001 = new Gson().fromJson(alleActiveMetrics001Result, Histogramms.STRING_SET_TYPE);
    Assert.assertNotNull(activeMerticsSet001);
    return activeMerticsSet001;
  }


}



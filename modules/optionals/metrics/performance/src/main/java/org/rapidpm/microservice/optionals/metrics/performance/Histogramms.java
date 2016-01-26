package org.rapidpm.microservice.optionals.metrics.performance;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.rapidpm.ddi.DI;
import org.rapidpm.proxybuilder.core.metrics.RapidPMMetricsRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by Sven Ruppert on 14.12.15.
 */
@Path("/metrics/performance/histogramms")
public class Histogramms {

  public static final Type HISTORY_SNAPSHOT_LIST_TYPE = new TypeToken<List<HistogrammSnapshot>>() {
  }.getType();
  public static final Type STRING_SET_TYPE = new TypeToken<Set<String>>() {
  }.getType();


  public static final String LIST_ALL_HISTOGRAMMS = "listAllHistogramms";
  public static final String LIST_ALL_HISTOGRAMM_NAMES = "listAllHistogrammNames";
  public static final String LIST_ALL_ACTIVATED_METRICS = "listAllActivateMetrics";

  public static final String LIST_ONE_HISTOGRAMMS = "listOneHistogramm";
  public static final String REMOVE_ONE_HISTOGRAMMS = "removeOneHistogramm";

  public static final String QUERY_PARAM_HISTOGRAMM_NAME = "histogrammName";
  public static final String QUERY_PARAM_CLASS_FQ_NAME = "classFQN";
  public static final String QUERY_PARAM_PKG_NAME = "pkgFQN";

  public static final String ACTIVATE_METRICS_FOR_CLASS = "activateMetricsForClass";
  public static final String ACTIVATE_METRICS_FOR_PKG = "activateMetricsForPKG";

  public static final String DE_ACTIVATE_METRICS_FOR_CLASS = "deActivateMetricsForClass";
  public static final String DE_ACTIVATE_METRICS_FOR_PKG = "deActivateMetricsForPKG";
  public static final String OK = "OK";
  public static final String NOT_OK = "pkgName null or empty";

  @GET()
  @Path(LIST_ALL_HISTOGRAMMS)
  @Produces(MediaType.APPLICATION_JSON)
  public String listAll() {
    final SortedMap<String, Histogram> histogramMap = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();
    final List<HistogrammSnapshot> histogrammSnapshots = new ArrayList<>();
    for (Entry<String, Histogram> entry : histogramMap.entrySet()) {
      final String key = entry.getKey();
      final Histogram histogram = entry.getValue();
      histogrammSnapshots.add(mapData(key, histogram));
    }
    return new Gson().toJson(histogrammSnapshots, HISTORY_SNAPSHOT_LIST_TYPE);
  }

  private HistogrammSnapshot mapData(final String key, final Histogram histogram) {
    final Snapshot histogramSnapshot = histogram.getSnapshot();
    return emptyHistogrammSnapshot(key)
        .histogramCount(histogram.getCount())
        .histogramSnapshot75thPercentile(histogramSnapshot.get75thPercentile())
        .histogramSnapshot95thPercentile(histogramSnapshot.get95thPercentile())
        .histogramSnapshot98thPercentile(histogramSnapshot.get98thPercentile())
        .histogramSnapshot99thPercentile(histogramSnapshot.get99thPercentile())
        .histogramSnapshot999thPercentile(histogramSnapshot.get999thPercentile())
        .histogramSnapshotMax(histogramSnapshot.getMax())
        .histogramSnapshotMean(histogramSnapshot.getMean())
        .histogramSnapshotMedian(histogramSnapshot.getMedian())
        .histogramSnapshotMin(histogramSnapshot.getMin())
        .histogramSnapshotStdDev(histogramSnapshot.getStdDev())
        .histogramSnapshotValues(histogramSnapshot.getValues());
  }

  private HistogrammSnapshot emptyHistogrammSnapshot(final String histogrammName) {
    return new HistogrammSnapshot().name(histogrammName);
  }

  @GET()
  @Path(LIST_ALL_HISTOGRAMM_NAMES)
  @Produces(MediaType.APPLICATION_JSON)
  public String listAllNames() {
    final SortedMap<String, Histogram> histogramMap = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();
    return new Gson().toJson(histogramMap.keySet(), Set.class);
  }

  @GET()
  @Path(LIST_ONE_HISTOGRAMMS)
  @Produces(MediaType.APPLICATION_JSON)
  public String listOneHistogramm(@QueryParam(QUERY_PARAM_HISTOGRAMM_NAME) final String histogrammName) {
    final SortedMap<String, Histogram> histograms = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();
    final HistogrammSnapshot histogrammSnapshot;
    if (histograms.containsKey(histogrammName)) {
      histogrammSnapshot = mapData(histogrammName, histograms.get(histogrammName));
    } else {
      histogrammSnapshot = emptyHistogrammSnapshot(histogrammName);
    }
    return new Gson().toJson(histogrammSnapshot, HistogrammSnapshot.class);
  }

  @GET()
  @Path(REMOVE_ONE_HISTOGRAMMS)
  @Produces(MediaType.APPLICATION_JSON)
  public String removeOneHistogramm(@QueryParam(QUERY_PARAM_HISTOGRAMM_NAME) final String histogrammName) {
    final MetricRegistry metricRegistry = RapidPMMetricsRegistry.getInstance().getMetrics();

    final SortedMap<String, Histogram> histograms = metricRegistry.getHistograms();

    final HistogrammSnapshot histogrammSnapshot;

    if (histograms.containsKey(histogrammName)) {
      histogrammSnapshot = mapData(histogrammName, histograms.get(histogrammName));
    } else {
      histogrammSnapshot = emptyHistogrammSnapshot(histogrammName);
    }

    metricRegistry.remove(histogrammName);
    return new Gson().toJson(histogrammSnapshot, HistogrammSnapshot.class);
  }


  @GET()
  @Path(LIST_ALL_ACTIVATED_METRICS)
  @Produces(MediaType.APPLICATION_JSON)
  public String listAllActivatedMetrics() {
    return new Gson().toJson(DI.listAllActivatedMetrics(), STRING_SET_TYPE);
  }


  //activate Metrics
  @GET()
  @Path(ACTIVATE_METRICS_FOR_CLASS)
  public String activateMetricsForClass(@QueryParam(QUERY_PARAM_CLASS_FQ_NAME) final String classFQN) {
    if (classFQN != null && !classFQN.isEmpty()) {
      try {
        final Class<?> aClass = Class.forName(classFQN);
        DI.activateMetrics(aClass);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return e.getMessage();
      }
    }
    return OK;
  }

  @GET()
  @Path(ACTIVATE_METRICS_FOR_PKG)
  @Produces(MediaType.TEXT_PLAIN)
  public String activateMetricsForPKG(@QueryParam(QUERY_PARAM_PKG_NAME) final String pkgName) {
    if (pkgName != null && !pkgName.isEmpty()) {
      DI.activateMetrics(pkgName);
      return OK;
    }
    return NOT_OK;
  }

  //deactivate Metrics
  @GET()
  @Path(DE_ACTIVATE_METRICS_FOR_CLASS)
  public String deActivateMetricsForClass(@QueryParam(QUERY_PARAM_CLASS_FQ_NAME) final String classFQN) {
    if (classFQN != null && !classFQN.isEmpty()) {
      try {
        final Class<?> aClass = Class.forName(classFQN);
        DI.deActivateMetrics(aClass);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return e.getMessage();
      }
      return OK;
    }
    return NOT_OK;
  }

  @GET()
  @Path(DE_ACTIVATE_METRICS_FOR_PKG)
  public String deActivateMetricsForPKG(@QueryParam(QUERY_PARAM_PKG_NAME) final String pkgName) {
    if (pkgName != null && !pkgName.isEmpty()) {
      DI.deActivateMetrics(pkgName);
      return OK;
    }
    return NOT_OK;
  }


}

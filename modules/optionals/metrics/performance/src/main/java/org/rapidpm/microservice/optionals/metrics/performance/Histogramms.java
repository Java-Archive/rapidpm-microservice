package org.rapidpm.microservice.optionals.metrics.performance;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.rapidpm.proxybuilder.core.metrics.RapidPMMetricsRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by svenruppert on 14.12.15.
 */
@Path("/metrics/performance/histogramms")
public class Histogramms {

  public static final Type HISTORY_SNAPSHOT_LIST_TYPE = new TypeToken<List<HistogrammSnapshot>>() {
  }.getType();
  public static final String LIST_ALL_HISTOGRAMMS = "listAllHistogramms";
  public static final String LIST_ALL_HISTOGRAMM_NAMES = "listAllHistogrammNames";
  public static final String LIST_ONE_HISTOGRAMMS = "listOneHistogramm";
  public static final String REMOVE_ONE_HISTOGRAMMS = "removeOneHistogramm";
  public static final String QUERY_PARAM_HISTOGRAMM_NAME = "histogrammName";

  @GET()
  @Path(LIST_ALL_HISTOGRAMMS)
  @Produces("application/json")
  public String listAll() {
    final SortedMap<String, Histogram> histogramMap = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();
    final List<HistogrammSnapshot> histogrammSnapshots = new ArrayList<>();
    for (Map.Entry<String, Histogram> entry : histogramMap.entrySet()) {
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
  @Produces("application/json")
  public String listAllNames() {
    final SortedMap<String, Histogram> histogramMap = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();
    return new Gson().toJson(histogramMap.keySet(), Set.class);
  }

  @GET()
  @Path(LIST_ONE_HISTOGRAMMS)
  @Produces("application/json")
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
  @Produces("application/json")
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

  //activate Metrics

  //deactivate Metrics





}

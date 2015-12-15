package org.rapidpm.microservice.optionals.metrics.performance;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.rapidpm.proxybuilder.core.metrics.RapidPMMetricsRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by svenruppert on 14.12.15.
 */
@Path("/metrics/performance/overview")
public class Overview {

  public final static Type HISTORY_SNAPSHOT_LIST_TYPE = new TypeToken<List<HistogrammSnapshot>>() {
  }.getType();
  public final static String LIST_ALL_HISTOGRAMMS = "listAllHistogramms";

  @GET()
  @Path(LIST_ALL_HISTOGRAMMS)
  @Produces("application/json")
  public String listAll() {
    final SortedMap<String, Histogram> histogramMap = RapidPMMetricsRegistry.getInstance().getMetrics().getHistograms();

    final List<HistogrammSnapshot> histogrammSnapshots = new ArrayList<>();

    for (Map.Entry<String, Histogram> entry : histogramMap.entrySet()) {
      final String key = entry.getKey();
      final Histogram histogram = entry.getValue();
      final Snapshot histogramSnapshot = histogram.getSnapshot();

      histogrammSnapshots
          .add(
              new HistogrammSnapshot()
                  .name(key)
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
                  .histogramSnapshotValues(histogramSnapshot.getValues()));
    }
    return new Gson().toJson(histogrammSnapshots, HISTORY_SNAPSHOT_LIST_TYPE);
  }


}

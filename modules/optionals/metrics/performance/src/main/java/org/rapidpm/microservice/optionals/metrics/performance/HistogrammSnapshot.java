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
package org.rapidpm.microservice.optionals.metrics.performance;

import java.io.Serializable;
import java.util.Arrays;

public class HistogrammSnapshot implements Serializable {


  private String name = "";

  private double histogramSnapshot75thPercentile;
  private double histogramSnapshot95thPercentile;
  private double histogramSnapshot98thPercentile;
  private double histogramSnapshot99thPercentile;
  private double histogramSnapshot999thPercentile;
  private long histogramSnapshotMax;
  private double histogramSnapshotMean;
  private double histogramSnapshotMedian;
  private long histogramSnapshotMin;
  private double histogramSnapshotStdDev;
  private long[] histogramSnapshotValues = new long[0];
  private long histogramCount;

  public HistogrammSnapshot name(final String name) {
    this.name = name;
    return this;
  }

  public HistogrammSnapshot histogramSnapshot75thPercentile(final double histogramSnapshot75thPercentile) {
    this.histogramSnapshot75thPercentile = histogramSnapshot75thPercentile;
    return this;
  }

  public HistogrammSnapshot histogramSnapshot95thPercentile(final double histogramSnapshot95thPercentile) {
    this.histogramSnapshot95thPercentile = histogramSnapshot95thPercentile;
    return this;
  }

  public HistogrammSnapshot histogramSnapshot98thPercentile(final double histogramSnapshot98thPercentile) {
    this.histogramSnapshot98thPercentile = histogramSnapshot98thPercentile;
    return this;
  }

  public HistogrammSnapshot histogramSnapshot99thPercentile(final double histogramSnapshot99thPercentile) {
    this.histogramSnapshot99thPercentile = histogramSnapshot99thPercentile;
    return this;
  }

  public HistogrammSnapshot histogramSnapshot999thPercentile(final double histogramSnapshot999thPercentile) {
    this.histogramSnapshot999thPercentile = histogramSnapshot999thPercentile;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotMax(final long histogramSnapshotMax) {
    this.histogramSnapshotMax = histogramSnapshotMax;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotMean(final double histogramSnapshotMean) {
    this.histogramSnapshotMean = histogramSnapshotMean;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotMedian(final double histogramSnapshotMedian) {
    this.histogramSnapshotMedian = histogramSnapshotMedian;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotMin(final long histogramSnapshotMin) {
    this.histogramSnapshotMin = histogramSnapshotMin;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotStdDev(final double histogramSnapshotStdDev) {
    this.histogramSnapshotStdDev = histogramSnapshotStdDev;
    return this;
  }

  public HistogrammSnapshot histogramSnapshotValues(final long[] histogramSnapshotValues) {
    this.histogramSnapshotValues = histogramSnapshotValues;
    return this;
  }

  public HistogrammSnapshot histogramCount(final long histogramCount) {
    this.histogramCount = histogramCount;
    return this;
  }

  @Override
  public String toString() {
    return "HistogrammSnapshot{" +
        "histogramCount=" + histogramCount +
        ", histogramSnapshot75thPercentile=" + histogramSnapshot75thPercentile +
        ", histogramSnapshot95thPercentile=" + histogramSnapshot95thPercentile +
        ", histogramSnapshot98thPercentile=" + histogramSnapshot98thPercentile +
        ", histogramSnapshot999thPercentile=" + histogramSnapshot999thPercentile +
        ", histogramSnapshot99thPercentile=" + histogramSnapshot99thPercentile +
        ", histogramSnapshotMax=" + histogramSnapshotMax +
        ", histogramSnapshotMean=" + histogramSnapshotMean +
        ", histogramSnapshotMedian=" + histogramSnapshotMedian +
        ", histogramSnapshotMin=" + histogramSnapshotMin +
        ", histogramSnapshotStdDev=" + histogramSnapshotStdDev +
        ", histogramSnapshotValues=" + Arrays.toString(histogramSnapshotValues) +
        ", name='" + name + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public double getHistogramSnapshot75thPercentile() {
    return histogramSnapshot75thPercentile;
  }

  public double getHistogramSnapshot95thPercentile() {
    return histogramSnapshot95thPercentile;
  }

  public double getHistogramSnapshot98thPercentile() {
    return histogramSnapshot98thPercentile;
  }

  public double getHistogramSnapshot99thPercentile() {
    return histogramSnapshot99thPercentile;
  }

  public double getHistogramSnapshot999thPercentile() {
    return histogramSnapshot999thPercentile;
  }

  public long getHistogramSnapshotMax() {
    return histogramSnapshotMax;
  }

  public double getHistogramSnapshotMean() {
    return histogramSnapshotMean;
  }

  public double getHistogramSnapshotMedian() {
    return histogramSnapshotMedian;
  }

  public long getHistogramSnapshotMin() {
    return histogramSnapshotMin;
  }

  public double getHistogramSnapshotStdDev() {
    return histogramSnapshotStdDev;
  }

  public long[] getHistogramSnapshotValues() {
    return histogramSnapshotValues;
  }

  public long getHistogramCount() {
    return histogramCount;
  }
}

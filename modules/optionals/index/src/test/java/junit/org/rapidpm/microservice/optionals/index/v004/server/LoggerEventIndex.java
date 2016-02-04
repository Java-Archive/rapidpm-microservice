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

package junit.org.rapidpm.microservice.optionals.index.v004.server;

import junit.org.rapidpm.microservice.optionals.index.v004.api.IndexOfTypeLoggerEvent;
import junit.org.rapidpm.microservice.optionals.index.v004.api.LoggerEvent;
import junit.org.rapidpm.microservice.optionals.index.v004.api.LoggerIndexAPIKt;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.jetbrains.annotations.NotNull;
import org.rapidpm.microservice.optionals.index.IndexManagement;
import org.rapidpm.microservice.optionals.index.stores.indices.BasicLuceneIndexOfType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggerEventIndex extends BasicLuceneIndexOfType<LoggerEvent> implements IndexOfTypeLoggerEvent {


  protected LoggerEventIndex(final String indexName) {
    super(indexName);
  }

  private LoggerEventIndex(final Builder builder) {
    super(builder.indexName);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public List<LoggerEvent> queryByExample(final LoggerEvent loggerEvent) {
    return query(LoggerIndexAPIKt.toLuceneQuery(loggerEvent));
  }

  @Override
  public List<LoggerEvent> query(final String query) {
    try {
      final Query q = new QueryParser(MESSAGE, analyzer).parse(query);
      final IndexSearcher searcher = new IndexSearcher(directoryReader);
      final TopScoreDocCollector collector = TopScoreDocCollector.create(1_00);
      searcher.search(q, collector);
      final ScoreDoc[] hits = collector.topDocs().scoreDocs;
      final List<LoggerEvent> result = new ArrayList<>();
      for (final ScoreDoc hit : hits) {
        final int doc = hit.doc;
        final Document document = searcher.doc(doc);
        document.get(LEVEL);
        result.add(new LoggerEvent()
            .level(document.get(LEVEL))
            .message(document.get(MESSAGE))
            .timestamp(LocalDateTime.parse(document.get(TIMESTAMP), IndexManagement.DATE_TIME_FORMATTER))
        );
      }
      return result;
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  @Override
  public Analyzer createAnalyzer() {
    return new StandardAnalyzer();
  }

  @Override
  @NotNull
  protected Document transform2Document(final LoggerEvent loggerEvent) {
    final Document doc = new Document();
    doc.add(new StringField(LEVEL, loggerEvent.getLevel(), Store.YES));
    doc.add(new StringField(CREATED, LocalDateTime.now().format(IndexManagement.DATE_TIME_FORMATTER), Store.YES));
    doc.add(new StringField(TIMESTAMP, loggerEvent.getTimestamp().format(IndexManagement.DATE_TIME_FORMATTER), Store.YES));
    doc.add(new TextField(MESSAGE, loggerEvent.getMessage(), Store.YES));
    return doc;
  }

  public static final class Builder {
    private String indexName;

    private Builder() {
    }

    @Nonnull
    public Builder withIndexName(@Nonnull final String indexName) {
      this.indexName = indexName;
      return this;
    }

    @Nonnull
    public LoggerEventIndex build() {
      return new LoggerEventIndex(this);
    }
  }
}

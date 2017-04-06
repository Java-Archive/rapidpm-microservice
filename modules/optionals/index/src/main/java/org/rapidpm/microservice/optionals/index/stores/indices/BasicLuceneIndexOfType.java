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

package org.rapidpm.microservice.optionals.index.stores.indices;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import java.io.File;
import java.io.IOException;


public abstract class BasicLuceneIndexOfType<T> implements IndexOfType<T> {

  protected final String indexName;

  protected final Analyzer analyzer;

  protected final IndexWriterConfig config;
  protected final IndexWriter indexWriter;
  protected final Directory directory;

  protected DirectoryReader directoryReader;

  protected BasicLuceneIndexOfType(final String indexName) {
    analyzer = createAnalyzer();
    config = new IndexWriterConfig(analyzer);
    this.indexName = indexName;
    try {
      final File baseDir = new File(IndexManagement.IDX_BASE_DIR);
      if (!baseDir.exists()) {
        baseDir.mkdir();
      }
      final File indexDirectory = new File(baseDir, indexName);
      if (!indexDirectory.exists()) {
        indexDirectory.mkdirs();
      }
      directory = FSDirectory.open(indexDirectory.toPath());
      indexWriter = new IndexWriter(directory, config);
      indexWriter.commit();
      directoryReader = DirectoryReader.open(directory);

    } catch (final IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Index  " + indexName + " could not instantiated " + this.getClass().getName());
    }

  }

  public abstract Analyzer createAnalyzer();

  @Override
  public void addElement(final T value) {
    writeDocument2Index(transform2Document(value));
  }

  @Override
  public void shutdown() {
    try {
      if (directoryReader != null) directoryReader.close();
      if (indexWriter != null) {
        indexWriter.commit();
        indexWriter.flush();
        indexWriter.close();
      }
      if (directory != null) directory.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void writeDocument2Index(final Document doc) {
    try {
      indexWriter.addDocument(doc);
      indexWriter.commit();
      final DirectoryReader ifChanged = DirectoryReader.openIfChanged(directoryReader);
      if (ifChanged != null) directoryReader = ifChanged;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected abstract Document transform2Document(final T value);
}

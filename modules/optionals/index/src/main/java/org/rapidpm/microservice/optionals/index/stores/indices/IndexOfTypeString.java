package org.rapidpm.microservice.optionals.index.stores.indices;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sven Ruppert on 18.01.16.
 */
public class IndexOfTypeString implements IndexOfType<String> {


  public static final String FIELD_TXT = "txt";


  private final String indexName;
  private final StandardAnalyzer analyzer = new StandardAnalyzer();
  private final IndexWriterConfig config = new IndexWriterConfig(analyzer);
  private final IndexWriter indexWriter;
  private final Directory directory;

  private DirectoryReader directoryReader;

  private IndexOfTypeString(final Builder builder) {
    indexName = builder.indexName;
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
      throw new RuntimeException("Index  " + indexName + " could not instanciated " + this.getClass().getName());
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }


  @Override
  public void addElement(final String s) {
    final Document doc = new Document();
    doc.add(new TextField(FIELD_TXT, s, Store.YES));
    doc.add(new StringField("created", LocalDateTime.now().format(IndexManagement.DATE_TIME_FORMATTER), Store.YES));
    try {
      indexWriter.addDocument(doc);
      indexWriter.commit();
      final DirectoryReader ifChanged = DirectoryReader.openIfChanged(this.directoryReader);
      if (ifChanged != null) this.directoryReader = ifChanged;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> queryByExample(final String s) {
    return Collections.emptyList();
  }

  @Override
  public List<String> query(final String query) {
    try {
      final Query q = new QueryParser(FIELD_TXT, analyzer).parse(query);
      final IndexSearcher searcher = new IndexSearcher(directoryReader);
      final TopScoreDocCollector collector = TopScoreDocCollector.create(1_00);
      searcher.search(q, collector);
      final ScoreDoc[] hits = collector.topDocs().scoreDocs;
      final List<String> result = new ArrayList<>();
      for (final ScoreDoc hit : hits) {
        result.add(hit.toString());
      }
      return result;
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
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
    public IndexOfTypeString build() {
      return new IndexOfTypeString(this);
    }
  }
}

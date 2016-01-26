package org.rapidpm.microservice.optionals.index.stores;

import org.rapidpm.microservice.optionals.index.IndexManagement;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfType;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sven Ruppert on 18.01.16.
 */
public class DefaultIndexStore implements IndexStore {


  private final Map<String, IndexOfType> indexOfTypeMap = new HashMap<>();

  public DefaultIndexStore() {
    System.out.println("DefaultIndexStore.... = ");
  }

  @Override
  public <I extends IndexOfType<T>, T> void addIndexOfType(final String indexName, final I indexOfType) {
    indexOfTypeMap.putIfAbsent(indexName, indexOfType);
  }

  @Override
  public boolean containsIndex(String indexName) {
    if (indexName != null) {
      return indexOfTypeMap.containsKey(indexName);
    } else {
      return false;
    }
  }

  @Override
  public boolean removeIndex(final String indexName) {
    if (containsIndex(indexName)) {
      final IndexOfType indexOfType = indexOfTypeMap.get(indexName);
      indexOfType.shutdown();
      indexOfTypeMap.remove(indexName);

      return deleteIndexDirectory(indexName);
    } else {
      return false;
    }
  }

  private boolean deleteIndexDirectory(final String indexName) {
    final File indexDirectory = new File(IndexManagement.IDX_BASE_DIR + "/" + indexName);
    if (indexDirectory.exists()) {
      try {
        Files.walkFileTree(indexDirectory.toPath(), new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
          }
        });
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;
  }


  @Override
  public void shutdownIndex(final String indexName) {
    if (containsIndex(indexName)) {
      indexOfTypeMap
          .get(indexName)
          .shutdown();
    }
  }

  @Override
  public void shutdownAll() {
    indexOfTypeMap
        .values()
        .stream()
        .forEach(IndexOfType::shutdown);
  }


  @Override
  public Set<String> getIndexNameSet() {
    return Collections.unmodifiableSet(indexOfTypeMap.keySet());
  }

  @Override
  public <I extends IndexOfType<T>, T> I getIndex(final String indexName) {
    return (I) indexOfTypeMap.get(indexName);
  }


}

package com.vecondev.bo;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FileUtil {

  private static final String HTML_SUFFIX = "html";

  private FileUtil() {}

  public static String getTemplateAsString(S3Object s3Object) {
    try {
      Path path = convertS3ObjectToPath(s3Object);
      String template = readFromFile(path);
      FileUtil.deleteFile(path.toFile());
      return template;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private static Path convertS3ObjectToPath(S3Object s3Object) throws IOException {
    S3ObjectInputStream inputStream = s3Object.getObjectContent();
    byte[] bytes = IOUtils.toByteArray(inputStream);
    Path tempFile = Files.createTempFile(UUID.randomUUID().toString(), HTML_SUFFIX);
    Files.write(tempFile, bytes);
    return tempFile;
  }

  private static String readFromFile(Path path) {
    try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path.toFile()))) {
      StringBuilder builder = new StringBuilder();
      String read;
      while ((read = reader.readLine()) != null) {
        builder.append(read);
      }
      return builder.toString();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void deleteFile(File file) {
    try {
      Files.deleteIfExists(file.toPath());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}

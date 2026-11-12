package com.noobdev;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.io.File;

public final class Path {
  private Path() {}

  public static String getRes() {
    String path = System.getProperty("user.dir") + "/src/main/resources";
    try {
      Files.createDirectories(Paths.get(path));
    } catch(IOException e) {
      e.printStackTrace();
    }
    return path;
  }

  public static Map<String, Object> readConfig(String path) {
    Map<String, Object> payload = null;
    try {
      FileInputStream stream = new FileInputStream(getRes() + "/" + path);
      Yaml config = new Yaml();
      payload = config.load(stream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return payload;
  }

  public static void writeConfig(String path, Map<String, Object> payload) {
    try (FileWriter writer = new FileWriter(getRes() + "/" + path)) {
      Yaml config = new Yaml();
      config.dump(payload, writer);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static String[] listNodes(String path) {
    try {
      File dir = new File(path);
      if (!dir.isDirectory()) {
        throw new IOException("Expected path to be directory");
      }
      return dir.list();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean resExist(String path) {
    File res = new File(getRes() + "/" + path);
    if (!res.exists()) {
      return false;
    }
    return true;
  }
}

package com.noobdev;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public final class Utility {
  private Utility() {}

  public static String getRes() {
    String path = System.getProperty("user.dir") + "/res";
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
}

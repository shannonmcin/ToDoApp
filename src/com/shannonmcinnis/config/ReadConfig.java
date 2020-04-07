package com.shannonmcinnis.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public abstract class ReadConfig {
  static Properties readConfig() {
    return readConfig("todo.properties");
  }

  static Properties readConfig(String fileName) {
    File file = getFile(fileName);
    Properties props = new Properties();

    try {
      FileReader reader = new FileReader(file);
      props.load(reader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return props;
  }

  private static File getFile(String fileName) {
    File file = new File(fileName);
    if(!file.exists()) {
      try {
        boolean b = file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return file;
  }
}

package com.JavaWebServer.app;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

public class FileEditor {

  public FileEditor() {}

  public void edit(String path, String edit) {
    File file = new File(path);
    try {
      Files.write(file.toPath(),edit.getBytes("UTF-8"));
    } catch (Exception e) {
      new Exception("Path not found:").printStackTrace();
      e.printStackTrace(); 
    }
  }
}

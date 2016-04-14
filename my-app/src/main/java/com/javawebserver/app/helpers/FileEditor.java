package com.javawebserver.app.helpers;

import com.javawebserver.app.helpers.ExceptionLogger;

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
      ExceptionLogger.logException("can't write to file" + e);
    }
  }
}

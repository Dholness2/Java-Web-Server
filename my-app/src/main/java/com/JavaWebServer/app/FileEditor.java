package com.JavaWebServer.app;

import java.io.File;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.IOException;

public class FileEditor {

  public FileEditor() {}

  public void edit(String path, String edit) {
    File file = new File(path);
    try {
      Files.write(file.toPath(),edit.getBytes("UTF-8"));
    } catch (Exception e) {
      System.out.println("cant read file" +e);
    }
  }

  private void createNewFile(String path, String edit) {
    try{
      PrintWriter writer = new PrintWriter(path, "UTF-8");
      writer.print(edit);
      writer.close();
    } catch (Exception e) {
      System.out.println("cant write line" +e);
    }
  }
}

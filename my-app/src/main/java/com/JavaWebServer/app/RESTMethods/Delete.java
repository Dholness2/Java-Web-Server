package com.JavaWebServer.app;

import java.io.PrintWriter;

public class Delete implements RestMethod {
  private String responseStatus;
  private String path;
  private String formField;
  private FileEditor editor;

  public Delete (String response, String path, FileEditor editor) {
    this.responseStatus = response;
    this.editor = editor;
    this.path = path;
  }

  public  byte [] handleRequest(Request request) {
    clearFile();
    return this.responseStatus.getBytes();
  }

  private void clearFile() {
   this.editor.edit(this.path, "");
  }
}

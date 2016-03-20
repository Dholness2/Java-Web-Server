package com.JavaWebServer.app;

import java.io.PrintWriter;

public class Delete implements RestMethod {
  private String responseStatus;
  private String fileName;
  private String directory;
  private FileEditor editor;

  public Delete (String response, String fileName, String directory, FileEditor editor) {
    this.responseStatus = response;
    this.editor = editor;
    this.fileName = fileName;
    this.directory = directory; 
  }

  public  byte [] handleRequest(Request request) {
    clearFile();
    return this.responseStatus.getBytes();
  }

  private void clearFile() {
   this.editor.edit(getPath(), "");
  }

  private String getPath() {
    return (this.directory +this.fileName);
  }
}

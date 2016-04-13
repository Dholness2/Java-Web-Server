package com.javawebserver.app.responses;

import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.helpers.FileEditor;
import com.javawebserver.app.Request;

import java.io.PrintWriter;

public class Delete implements Response {
  private ResponseBuilder responseBuilder;
  private String fileName;
  private String directory;
  private FileEditor editor;
  private static final String OK_CODE = "200";

  public Delete (ResponseBuilder responseBuilder, String fileName, String directory, FileEditor editor) {
    this.responseBuilder= responseBuilder;
    this.editor = editor;
    this.fileName = fileName;
    this.directory = directory;
  }

  public byte[] handleRequest(Request request) {
    clearFile();
    return this.responseBuilder.getStatus(OK_CODE);
  }

  private void clearFile() {
   this.editor.edit(getPath(), "");
  }

  private String getPath() {
    return (this.directory +this.fileName);
  }
}

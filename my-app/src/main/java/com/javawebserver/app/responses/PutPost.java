package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.Request;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.helpers.FileEditor;

public class PutPost implements Response {
  private ResponseBuilder responseBuilder;
  private String path;
  private String fileName;
  private FileEditor editor;
  private String directory;

  private static final String OK_STATUS_CODE ="200";

  public PutPost(ResponseBuilder responseBuilder, String fileName, String directory, FileEditor editor) {
    this.responseBuilder = responseBuilder;
    this.editor = editor;
    this.path = path;
    this.fileName = fileName;
    this.directory = directory;
  }

  public  byte [] handleRequest(Request request) {
    String body = request.getBody();
    if(body != null){
      editFile(body);
    }
    return this.responseBuilder.getStatus(OK_STATUS_CODE);
  }

  private void editFile(String body) {
    this.editor.edit(getPath(), body);
  }

  private String getPath() {
    return (this.directory + this.fileName);
  }
}

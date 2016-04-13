package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class GetForm extends Get {
  private String path;
  private File currentFile;
  private String form;

  public GetForm(ResponseBuilder response, String fileName, String contentType, String directory, boolean protectedRoute, String form) {
    super(response, fileName, contentType, directory, protectedRoute);
    this.form = form;
  }

  public byte[] handleRequest(Request request) {
    buildPath();
    if (hasFile()){
      return super.handleRequest(request);
    }
    return getNewFormResponse(request);
  }

  public void buildPath() {
    this.path = this.directory + this.fileName;
    this.currentFile = new File(path);
  }

  public boolean hasFile() {
    return (this.currentFile.exists());
  }

  public byte[] getNewFormResponse(Request request) {
    buildFormFile();
    return super.handleRequest(request);
  }

  public void buildFormFile() {
    try {
      byte[] formData = this.form.getBytes("UTF-8");
      Path file = this.currentFile.toPath();
      Files.write(file,formData);
    } catch (Exception e) {
      System.out.println("can not write new file" + e);
    }
  }
}

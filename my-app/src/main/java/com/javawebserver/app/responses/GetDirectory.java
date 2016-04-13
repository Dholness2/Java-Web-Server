package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;

import java.io.File;
import java.io.FileFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class GetDirectory implements Response {
  private String directoryPath;
  private ResponseBuilder responseBuilder;
  private boolean rootDirectory = false;
  private String rootPath;
  private static final String OK_STATUS_CODE = "200";
  private static final String CRLF = System.getProperty("line.separator");

  public GetDirectory(String directoryPath, ResponseBuilder responseBuilder, String rootPath) {
    this.directoryPath = directoryPath;
    this.responseBuilder = responseBuilder;
    this.rootPath = rootPath;
  }

  public byte[] handleRequest(Request request) {
    String body = buildbody((getDirectoryList(this.directoryPath)));
    return getResponse(body);
  }

  private byte[] getResponse(String responseBody) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    byte [] body = responseBody.getBytes();
    buildResponse(currentResponse, body);
    return currentResponse.getResponse();
  }

  private void buildResponse(ResponseBuilder builder, byte[] body) {
    builder.addStatus(OK_STATUS_CODE);
    builder.addHeader("Content-Type: ", "text/html");
    builder.addHeader("Content-Length: ", Integer.toString(body.length));
    builder.addBody(body);
  }

  private String buildbody(File[] dirList) {
    StringBuilder htmlBuilder = new StringBuilder();
    htmlBuilder.append("<!DOCTYPE html><html><body>");
    Arrays.sort(dirList);
    for(File file: dirList) {
      buildFileLink(file, htmlBuilder);
    }
    return ((htmlBuilder.toString().trim()) + "</body></html>");
  }

  private void buildFileLink(File file, StringBuilder htmlBuilder) {
    if (isRootDirectory()) {
      buildRootFileLink(file, htmlBuilder);
    } else {
      buildNestedFileLink(file, htmlBuilder);
    }
  }

  private boolean isRootDirectory() {
    return rootPath.equals(directoryPath);
  }

  private void buildRootFileLink(File file, StringBuilder htmlBuilder) {
    String fileName = file.getName();
    htmlBuilder.append("<a href=\"/"+ fileName +"\">"+fileName+"</a><br>"+CRLF);
  }

  private void buildNestedFileLink(File file, StringBuilder htmlBuilder) {
    String fileName =  file.getName();
    String pathName =  getPath(file);
    htmlBuilder.append("<a href=\""+ pathName +"\">"+ fileName +"</a><br>"+CRLF);
  }

  private String getPath(File file) {
   return file.getAbsolutePath().split(this.rootPath)[1];
  }

  private File[] getDirectoryList (String path) {
    File directory = new File(path);
    return directory.listFiles(getFileFilter());
  }

  private FileFilter getFileFilter() {
    return new FileFilter() {
      @Override
        public boolean accept(File file) {
          return !file.isHidden();
        }
    };
  }
}

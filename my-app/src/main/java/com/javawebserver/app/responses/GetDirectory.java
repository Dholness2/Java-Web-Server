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
  private boolean rootDirectory = false;
  private static final String CRLF = System.getProperty("line.separator");
  private String directoryPath;

  public GetDirectory(String directoryPath) {
    this.directoryPath = directoryPath;
  }

  public void setRootDirectory(boolean directoryStatus) {
    this.rootDirectory = directoryStatus;
  }

  public boolean isRootDirectory() {
    return this.rootDirectory;
  }

  public byte[]  handleRequest(Request request) {
    String body = buildbody((getDirectoryList(this.directoryPath)));
    return getResponse(body);
  }

  private byte[] getResponse(String responseBody) {
    ResponseBuilder builder = new HttpResponseBuilder();
    byte [] body = responseBody.getBytes();
    buildResponse(builder, body);
    return builder.getResponse();
  }

  private void buildResponse(ResponseBuilder builder, byte[] body) {
    builder.addStatus("OK");
    builder.addHeader("Content-Type: ", "text/html");
    builder.addHeader("Content-Length: ", Integer.toString(body.length));
    builder.addBody(body);
  }

  private String buildbody(File [] dirList) {
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
  private void buildRootFileLink(File file, StringBuilder htmlBuilder) {
    String fileName = file.getName();
    htmlBuilder.append("<a href=\"/"+ fileName +"\">"+fileName+"</a><br>"+CRLF);
  }

  private void buildNestedFileLink(File file, StringBuilder htmlBuilder) {
    String fileName =  file.getName();
    String pathName = file.getParentFile().getName() + "/" + fileName;
    htmlBuilder.append("<a href=\"/"+ pathName +"\">"+ fileName +"</a><br>"+CRLF);
  }

  private File [] getDirectoryList (String path) {
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

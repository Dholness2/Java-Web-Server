package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.encoders.Encoder;
import com.javawebserver.app.helpers.FileEditor;
import com.javawebserver.app.helpers.ExceptionLogger;
import com.javawebserver.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Paths;

import java.io.IOException;

public class Patch implements Response {
  private ResponseBuilder responseBuilder;
  private String fileName;
  private String directory;
  private FileEditor editor;
  private Encoder encoder;

  private final static String CRLF = System.getProperty("line.separator");
  private final static String MATCHER_HEADER = "If-Match: ";
  private final static String NO_CONTENT_CODE = "204";
  private final static String NOT_FOUND_CODE = "404";

  public Patch(ResponseBuilder responseBuilder, String fileName, String directory, FileEditor editor, Encoder encode) {
    this.responseBuilder = responseBuilder;
    this.editor = editor;
    this.fileName = fileName;
    this.directory = directory;
    this.encoder = encode;
  }

  public byte[] handleRequest(Request request) {
    String body = request.getBody();
    if ((body != null) && (matchedEtag(request))) {
      editFile(body);
      return responseBuilder.getStatus(NO_CONTENT_CODE);
    }
    return responseBuilder.getStatus(NOT_FOUND_CODE);
  }

  private void editFile(String body) {
    String path = this.directory+ this.fileName;
    this.editor.edit(path,body);
  }

  private boolean matchedEtag(Request request) {
    String requestTag = getEtag(request.getHeaders());
    String currentTag = getCurrentFileTag();
    return (currentTag.equals(requestTag));
  }

  private String  getCurrentFileTag() {
    String tag = "";
    try {
      byte [] fileBytes = Files.readAllBytes(Paths.get(this.directory+ this.fileName));
      return this.encoder.encode(fileBytes);
    } catch (IOException e) {
      ExceptionLogger.logException("File not found:");
    }
    return tag;
  }

  private String getEtag(String header) {
    String match = header.split(MATCHER_HEADER)[1];
    return match.substring(0, match.indexOf(CRLF));
  }
}

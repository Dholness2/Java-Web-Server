package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.encoders.Encoder;
import com.javawebserver.app.helpers.FileEditor;
import com.javawebserver.app.helpers.ExceptionLogger;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Paths;

import java.io.IOException;

public class Patch implements Response {
  private StatusCodes status;
  private String fileName;
  private String directory;
  private FileEditor editor;
  private Encoder encoder;
  private String CRLF = System.getProperty("line.separator");
  private String matchHeader = "If-Match: ";

  public Patch(StatusCodes status, String fileName, String directory, FileEditor editor, Encoder encode) {
    this.status = status;
    this.editor = editor;
    this.fileName = fileName;
    this.directory = directory;
    this.encoder = encode;
  }

  public  byte [] handleRequest(Request request) {
    String body = request.getBody();
    if ((body != null) && (matchedEtag(request))) {
      editFile(body);
      return status.NO_CONTENT.getBytes();
    }
   return status.NOT_FOUND.getBytes();
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
    try{
      byte [] fileBytes = Files.readAllBytes(Paths.get(this.directory+ this.fileName));
      return this.encoder.encode(fileBytes);
    } catch (IOException e) {
      ExceptionLogger.logException("File not found:");
    }
    return tag;
  }


 private String getEtag(String header) {
  String match = header.split(matchHeader)[1];
  String tag  = match.substring(0, match.indexOf(CRLF));
  return tag;
 }
}

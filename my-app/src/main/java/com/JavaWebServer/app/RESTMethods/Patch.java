package com.JavaWebServer.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Paths;

import java.io.IOException;

public class Patch implements RestMethod {
  private StatusCodes status;
  private String path;
  private String formField;
  private FileEditor editor;
  private Encoder encoder;
  private String CRLF = System.getProperty("line.separator");
  private String matchHeader = "If-Match: ";

  public Patch(StatusCodes status, String path, FileEditor editor, Encoder encode) {
    this.status = status;
    this.editor = editor;
    this.path = path;
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
    this.editor.edit(this.path, body);
  }

  private boolean matchedEtag(Request request) {
   String requestTag = getEtag(request.getHeaders());
   String currentTag = getCurrentFileTag();
   return (currentTag.equals(requestTag));
  }

  private String  getCurrentFileTag() {
    String tag = "";
      try{
        byte [] fileBytes = Files.readAllBytes(Paths.get(this.path));
        return this.encoder.encode(fileBytes);
      } catch (IOException exception) {
        System.out.println("Can not read file" + exception);
      }
    return tag;
  }


 private String getEtag(String header) {
  String match = header.split(matchHeader)[1];
  String tag  = match.substring(0, match.indexOf(CRLF));
  System.out.println(tag);
  return tag;
 }
}

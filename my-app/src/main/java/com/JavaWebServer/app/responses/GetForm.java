package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.Authenticator;

import java.io.File;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;

public class GetForm extends Get {
  private String path;

  public GetForm (StatusCodes status, String fileName, String contentType, String directory, boolean protectedRoute, String form) {
    super(status,fileName,contentType,directory,protectedRoute);
  }

  public byte [] handleRequest (Request request){
    if  (hasFile()){
      return super.handleRequest(request);
    }
    return null;
  }

  public boolean hasFile() {
    this.path = this.directory + this.fileName;
    File file = new File(path);
    return (file.exists());
  }

  public getNewFormResponse () {
    editFile();

  }

  private editFile() {

  }

  private byte [] buildResponse () {
    String file = this.file.toString()
    return (status.OK +CRLF+TYPEHEADER
        + this.contentType+CRLF+LENGTHHEADER
        + file.length() +CRLF+CRLF+file).getBytes();
  }

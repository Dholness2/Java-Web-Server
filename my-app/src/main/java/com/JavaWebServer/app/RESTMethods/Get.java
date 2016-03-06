package com.JavaWebServer.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;

public class Get implements RestMethod {
  private String status;
  private String fileName;
  private String directory;
  private String contentType;
  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  
  private static final String STATUSERROR = "HTTP/1.1 500 Internal Server Error";
  private static final String CRLF ="\r\n";
  private static final String TYPEHEADER = "Content-Type: ";
  private static final String LENGTHHEADER = "Content-Length:";

  public Get (String status, String fileName, String contentType, String directory) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
  }

  public Get (String status) {
    this.status = status;
  }

  public byte[] handleRequest(Request request) {
    if (this.fileName != null) {
      try {
        Path path = getPath();
       return getResponse(path);
      }catch (IOException e) {
        System.out.println("path not found"+ e);
      }
    }
    return status.getBytes();
  }

  private byte [] getResponse(Path path) {
    try {
      return fileResponse(Files.readAllBytes(path));
    }catch (IOException e) {
      System.out.println("path not found"+ e);
    }
    return STATUSERROR.getBytes();
  }

  private byte [] fileResponse(byte [] file) {
    try{
      this.outputStream.write(buildHeader(file.length));
      this.outputStream.write(file);
      return outputStream.toByteArray();
    } catch (IOException e) {
      System.out.println("could not write file" + e);
      return STATUSERROR.getBytes();
    }
  }

  private byte [] buildHeader (int fileLength) {
    return (status +CRLF+TYPEHEADER
            + contentType+CRLF+LENGTHHEADER
            + fileLength +CRLF+CRLF).getBytes();
  }
  
  private Path getPath() throws IOException {
    String location = this.directory + this.fileName;
    Path path = Paths.get(location);
    return path;
  }
}

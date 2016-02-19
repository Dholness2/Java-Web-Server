package com.JavaWebServer.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.ByteArrayOutputStream;


public class Get implements RestMethod {
  private String status;
  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private String fileName;
  private String CRLF ="\r\n";
  private String directory;
  private String contentType;

  public Get (String status, String fileName, String contentType, String directory) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;

  }

  public Get (String status) {
    this.status = status;

  }

  public byte[] handleRequest(Request request)  {
    if (this.fileName != null) {
      try {
        byte [] file = getfile();
        this.outputStream.write(buildHeader(file.length));
        this.outputStream.write(file);
        return outputStream.toByteArray();
      } catch( IOException e) {
        System.out.println("cant read request"+ e);
      }
    }
    return status.getBytes();
  }


  private byte [] buildHeader (int fileLength) {
    return (status +CRLF+"Content-Type: "
            + contentType+CRLF+"Content-Length:"
            + fileLength +CRLF+CRLF).getBytes();
  }

  private byte [] getfile() throws IOException {
    String location = this.directory + this.fileName;
    Path path = Paths.get(location);
    return Files.readAllBytes(path);
  }
}

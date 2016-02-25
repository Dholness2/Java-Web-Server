package com.JavaWebServer.app;

import java.io.File;
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
  private String fileName;
  private String CRLF ="\r\n";
  String typeHeader = "Content-Type: ";
  String contentLength = "Content-Length:";
  private String directory;
  private String contentType;
  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  
  public Get (String status, String fileName, String contentType, String directory) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;

  }

  public Get (String status) {
    this.status = status;

  }

  public byte[] handleRequest()  {
    if (this.fileName != null) {
      Path path;
      try {
      path = getPath();
      if (Files.isDirectory(path)) {
        return directoryResponse(path); 
      }else if (Files.isRegularFile(path)) {
        return fileResponse(Files.readAllBytes(path));
      }
      }catch (IOException e) {
        System.out.println(e);
       }
    }
      return status.getBytes();
   }
  
  private byte [] directoryResponse(Path directory)throws IOException {
    File file = new File (directory.toString());
    String [] fileList = file.list();
    StringBuffer files = new StringBuffer();
    int size = fileList.length;
    for (int i = 0; i < size; i++) {
      files.append(fileList[i]);
      files.append(CRLF);
    }
    byte [] body = (files.toString()).getBytes();
    return fileResponse(body);
  }

  private byte [] fileResponse(byte [] file) throws IOException {
    this.outputStream.write(buildHeader(file.length));
    this.outputStream.write(file);
    return outputStream.toByteArray();
  }
  
  private byte [] buildHeader (int fileLength) {
    return (status +CRLF+typeHeader
            + contentType+CRLF+contentLength
            + fileLength +CRLF+CRLF).getBytes();
  }

  private Path getPath() throws IOException {
    String location = this.directory + this.fileName;
    Path path = Paths.get(location);
    return path;
  }
}

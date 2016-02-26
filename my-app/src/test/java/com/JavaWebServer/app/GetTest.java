package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GetTest {
  private StatusCodes codes = new StatusCodes();
  private String directory = "/Users/don/desktop/cob_spec/public/";
  private String CRLF = "\r\n";

  @Test
  public void handleRequestTest() {
    Get testGet = new Get(codes.OK);
    byte [] response = testGet.handleRequest();
    assertEquals(codes.OK, new String(response));
  }

  @Test
  public void handleRequestContentImageTest() throws IOException {
     String fileName = "image.jpeg";
     String type = "image/jpeg";
     String path = directory + fileName;
     Get testGet = new Get(codes.OK,fileName,type, directory);
     byte [] testResponse = testGet.handleRequest();
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentfileTest() throws IOException {
     String fileName = "file1";
     String type = "txt/plain";
     String path = directory + fileName;
     Get testGet = new Get(codes.OK,fileName,type, directory);
     byte [] testResponse = testGet.handleRequest();
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test 
  public void handleRequestDirectoryTest() throws IOException{
    Get testGet = new Get(codes.OK,"public","text/plain",rootPath(directory)); 
    byte [] testResponse = testGet.handleRequest();
    String responseDirectory = (new String(testResponse).split((CRLF +CRLF))[1]);
    String expectedDirectory = getDirectoryListing(directory);
    assertEquals(expectedDirectory,responseDirectory);
  }

  private byte [] getFile(String path) throws IOException {
    Path location = Paths.get(path);
    return Files.readAllBytes(location);
  }


  private String rootPath(String path) {
    int indexOfLastFolder = path.lastIndexOf("/",((path.length())-2));
    return ((path.substring(0 ,indexOfLastFolder)) + "/");
  }

  private String buildHeader (String path, String contentType) throws IOException{
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    int fileLength = getFile(path).length;
    return (codes.OK +CRLF+typeHeader
        + contentType+CRLF+contentLength
        + fileLength);
  }

  private String getDirectoryListing(String path)throws IOException {
    String [] fileList = new File(path).list();
    StringBuffer files = new StringBuffer();
    int size = fileList.length;
    for (int i = 0; i < size; i++) {
      files.append(fileList[i]);
      files.append(CRLF);
    }
    return files.toString();
  }
}

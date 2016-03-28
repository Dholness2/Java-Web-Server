package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.responses.Get;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GetTest {
  private StatusCodes codes = new StatusCodes();
  private String directory = System.getProperty("user.dir")+"/public/";
  private String CRLF = "\r\n";
  private boolean protectedRoute = false;

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

  private byte [] getFile(String path) throws IOException {
    Path location = Paths.get(path);
    return Files.readAllBytes(location);
  }

  @Test
  public void handleSimepleRequestTest() {
    Get testGet = new Get(codes.OK);
    byte [] response = testGet.handleRequest(new Request());
    assertEquals(codes.OK, new String(response));
  }

  @Test
  public void unauthorizedTest() {
    String type = "text/plain";
    String fileName = "logs";
    boolean protectedRoute = true;
    Get testGet = new Get(codes,fileName,type,directory,true);
    String response = (new String(testGet.handleRequest(new Request())));
    String expectedResponse = (codes.UNAUTHORIZED+CRLF+"WWW-Authenticate: Basic realm=logs"+CRLF);
    assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentImageJpegTest() throws IOException {
     String fileName = "image.jpeg";
     String type = "image/jpeg";
     String path = directory + fileName;
     Get testGet = new Get(codes,fileName,type, directory, protectedRoute);
     byte [] testResponse = testGet.handleRequest(new Request());
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentImagePngTest() throws IOException {
     String fileName = "image.png";
     String type = "image/png";
     String path = directory + fileName;
     Get testGet = new Get(codes,fileName,type, directory,protectedRoute);
     byte [] testResponse = testGet.handleRequest(new Request());
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentImageGifTest() throws IOException {
     String fileName = "image.gif";
     String type = "image/gif";
     String path = directory + fileName;
     Get testGet = new Get(codes,fileName,type, directory, protectedRoute);
     byte [] testResponse = testGet.handleRequest(new Request());
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentfileTest() throws IOException {
     String fileName = "file1";
     String type = "txt/plain";
     String path = directory + fileName;
     Get testGet = new Get(codes,fileName,type, directory, protectedRoute);
     byte [] testResponse = testGet.handleRequest(new Request());
     String expectedResponse = buildHeader(path,type);
     String response =((new String(testResponse)).split((CRLF +CRLF))[0]);
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestMissingFileTest() {
    Get testGet = new Get(codes,"images.gif","image/gif", directory, protectedRoute);
    byte [] response = testGet.handleRequest(new Request());
    String testResponse = new String(response);
    String expectedResponse = "HTTP/1.1 500 Internal Server Error";
    assertEquals(expectedResponse,testResponse);
  }
}
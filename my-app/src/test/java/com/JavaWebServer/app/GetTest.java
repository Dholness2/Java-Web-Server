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

public class GetTest {
  private StatusCodes codes = new StatusCodes();
  private Request testRequest = new Request();
  private String CRLF = "\r\n";
  private ByteArrayOutputStream outputStream;
  private String typeHeader = "Content-Type: ";
  private String contentLength = "Content-Length:";

  @Test
  public void handleRequestTest() {
    testRequest.setMessage("GET / HTTP/1.1");
    Get testGet = new Get(codes.OK);
    byte [] response = testGet.handleRequest(testRequest);
    assertEquals(codes.OK, new String(response));
  }

  @Test
  public void handleImageRequestTest() throws IOException {
     String directory =  "/Users/don/desktop/cob_spec/public/"; 
     String fileName = "image.jpeg";
     String path = directory + fileName;
     testRequest.setMessage("GET /image.jpeg HTTP/1.1");
     Get testGet = new Get(codes.OK,fileName,"image/jpeg", directory);
     byte [] response = testGet.handleRequest(testRequest);
     byte [] body = ((new String(response)).split("\r\n\r\n")[1]).getBytes();
     System.out.println(getFile(path).equals(body));
  }

  @Test 
    public void handleRequestDirectoryTest() {
      String directory = "/Users/don/desktop/Java_Web_Server/my-app/"; 
      testRequest.setMessage ("Get / HTTP/1.1");
      Get testGet = new Get(codes.OK,"public","text/plain",directory);
      byte [] reponse = testGet.handleRequest(testRequest);
      // assertEquasl (getfile;
    }

  private byte [] getFile(String path) throws IOException {
   Path location = Paths.get(path);
   return Files.readAllBytes(location);
  }

  private byte [] fileResponse(byte [] file, String type) throws IOException {
    outputStream = new ByteArrayOutputStream();
    outputStream.write(buildHeader(file.length));
    outputStream.write(file);
    return outputStream.toByteArray();
  }

  private byte [] buildHeader (int fileLength, String contentType) {
    return (codes.OK +CRLF+typeHeader
            + contentType+CRLF+contentLength
            + fileLength +CRLF+CRLF).getBytes();
  }
}

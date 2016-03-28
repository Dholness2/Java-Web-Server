package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.responses.GetRedirect;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.helpers.FileEditor;

import static org.junit.Assert.assertEquals;

public class GetRedirectTest {
private String locationHeader = "Location: http://localhost:5000/";
private String CRLF = System.getProperty("line.separator");

private int port = 5000;
private StatusCodes codes;
private Request request = new Request();
private String serverName = "http://localhost:";
  
  @Test
  public void handleRequestTest(){
    request.setMessage("GET /redirect HTTP/1.1");
    String expectedResponse = codes.FOUND+CRLF+locationHeader;
    RestMethod testRedirect = new GetRedirect(codes,port,serverName);
    String response = new String (testRedirect.handleRequest(request));
    assertEquals(response,expectedResponse);
  }

}

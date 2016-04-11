package com.javawebserver.app.responses;

import org.junit.Test;
import org.junit.Before;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.GetRedirect;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

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
    Response testRedirect = new GetRedirect(codes,port,serverName);
    String response = new String (testRedirect.handleRequest(request));
    assertEquals(response,expectedResponse);
  }

}

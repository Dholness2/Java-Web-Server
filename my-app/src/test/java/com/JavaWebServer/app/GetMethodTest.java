package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class GetMethodTest {


  @Test 
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("GET / htttp1.1");
    GetMethod testGet = new GetMethod();
    String response = testGet.handleRequest(testrequest);
    assertEquals("HTTP/1.1 200 ok", response);
  }

  @Test 
  public void setNextMethodTest() {
    GetMethod testGet = new GetMethod();
    //RestMethod testPut = new PutMethod();
  }
}

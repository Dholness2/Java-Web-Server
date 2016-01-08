package com.JavaWebServer.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ServerTest  {

  @Test 
  public void creatsSeverObject () {
      Server testServer = new Server();
      int port  = testServer.getPort();
      assertEquals(5000, port);
  }
}

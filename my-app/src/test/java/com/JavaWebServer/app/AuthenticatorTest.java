package com.JavaWebServer.app;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AuthenticatorTest {
 private  Request testRequest = new Request();
 private static String CRLF = System.getProperty("line.separator");

 @Test
 public void requestAuthenticateTest() {
    testRequest.setHeaders("Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ="+CRLF );
    boolean validCredentials =  Authenticator.authenticate(testRequest);
    assertEquals(true,validCredentials);
  }

  @Test
  public void requestAuthenticateInvalidCredentialTest() {
    testRequest.setHeaders("Authorization: Basic V3JvbmdQYXNzd29yZA=="+CRLF );
    boolean validCredentials =  Authenticator.authenticate(testRequest);
    assertEquals(false,validCredentials);
  }
}

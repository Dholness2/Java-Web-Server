package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class base64DecoderTest {
  private base64Decoder base64Test = new base64Decoder();

  @Test
  public void decodeTest() {
    String encodedCredential = "dXNlcm5hbWU6cGFzc3dvcmQ=";
    String decodedCredentials = "username:password";
    String results = base64Test.decode(encodedCredential);
    assertEquals(decodedCredentials, results);
  }
}

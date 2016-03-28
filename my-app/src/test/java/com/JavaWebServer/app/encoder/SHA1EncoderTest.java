package com.JavaWebServer.app;

import com.JavaWebServer.app.encoders.Encoder;
import com.JavaWebServer.app.encoders.SHA1Encoder;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SHA1EncoderTest {
  @Test
  public void encodeTestiDefault()  throws Exception {
    byte [] testContent = ("default content").getBytes("utf-8");
    String expectedEncodedString = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    Encoder  testEncoder = new SHA1Encoder();
    assertEquals(expectedEncodedString,testEncoder.encode(testContent));
  }

  @Test
  public void encodeTestPatched()  throws Exception {
    byte [] testContent = ("patched content").getBytes("utf-8");
    String expectedEncodedString = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";
    Encoder  testEncoder = new SHA1Encoder();
    assertEquals(expectedEncodedString,testEncoder.encode(testContent));
  }
}

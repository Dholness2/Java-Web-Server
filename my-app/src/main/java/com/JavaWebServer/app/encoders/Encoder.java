package com.JavaWebServer.app.encoders;

import java.io.UnsupportedEncodingException;

public interface Encoder {
  public String encode(byte [] content);
}

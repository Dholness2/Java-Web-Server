package com.javawebserver.app.encoders;

import java.io.UnsupportedEncodingException;

public interface Encoder {
  public String encode(byte[] content);
}

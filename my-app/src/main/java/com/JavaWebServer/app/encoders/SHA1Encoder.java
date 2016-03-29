package com.JavaWebServer.app.encoders;

import com.JavaWebServer.app.encoders.Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class SHA1Encoder implements Encoder {

  public SHA1Encoder () {}

  public String encode(byte [] content) {
    String sha1 = "";
    try{
      MessageDigest messageAlgorithm = MessageDigest.getInstance("SHA-1");
      sha1  = byteToHex(messageAlgorithm.digest(content));
    } catch (NoSuchAlgorithmException exception) {
      System.out.println("No Such Algorithm"+ exception);
    }
    return sha1;
  }

  private String byteToHex (byte [] content) {
    StringBuilder builder = new StringBuilder();
    int length = content.length;
    for (int index = 0; index < length; index++) {
      builder.append(String.format("%02x",content[index]));
    }
    return builder.toString();
  }
}

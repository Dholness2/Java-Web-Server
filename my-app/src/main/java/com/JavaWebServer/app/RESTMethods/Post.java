package com.JavaWebServer.app;

public class Post implements RestMethod {
  private String responseStatus;

  public Post(String response) {
    this.responseStatus = response;
  }

  public byte [] handleRequest(Request request) {
    return this.responseStatus.getBytes();
  }
}

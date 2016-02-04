package com.JavaWebServer.app;

public class Request {
 private String request;

 public Request (String request) {
  this.request = request;
 }

 public String getRoute() {
   return this.request.split(" ")[1];
 }
}

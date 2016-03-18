package com.JavaWebServer.app;

public class StatusCodes {
  public static final String OK = "HTTP/1.1 200 OK";
  public static final String NOT_FOUND = "HTTP/1.1 404 Not Found";
  public static final String FOUND = "HTTP/1.1 302 FOUND";
  public static final String PARTIAL = "HTTP/1.1 206 Partial Content";
  public static final String RANGE_NOT_SATSIFIABLE = "HTTP/1.1 416 Range Not Satisfiable";
  public static final String STATUSERROR = "HTTP/1.1 500 Internal Server Error";
  public static final String NO_CONTENT ="HTTP/1.1 204 NO CONTENT";
  public static final String UNAUTHORIZED = "HTTP/1.1 401 UNAUTHORIZED";
  public static final String STATUSERROR = "HTTP/1.1 500 Internal Server Error";
}

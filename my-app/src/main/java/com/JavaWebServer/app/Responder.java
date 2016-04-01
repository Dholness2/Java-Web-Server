package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;

import java.util.Map;
import java.util.HashMap;

public class Responder {

  private static final String NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";
  private static final String NOT_FOUND = "HTTP/1.1 404 Not Found"; 
  private static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request";
  private static final String CRLF = System.getProperty("line.separator");

  private Map <String, Response> routes;

  public Responder(Map <String, Response> routes) {
    this.routes = routes;
  }

  public byte[] getResponse(Request request) {
    if (request.validRequest()){
      return handleValidRequest(request);
    } else {
      return BAD_REQUEST.getBytes();
    }
  }

  private byte[] handleValidRequest(Request request) {
    if (isValidPath(request)) {
      return handleValidRoute(request);
    }else{
      return NOT_FOUND.getBytes();
    }
  }

  private byte[] handleValidRoute(Request request) {
    if(checkMethod(request)) {
      return getMessage(request);
    } else {
      return (NOT_ALLOWED).getBytes();
    }
  }

  private byte[] getMessage(Request request) {
    String message = request.getRequest();
    Response currentRoute = routes.get(message);
    return currentRoute.handleRequest(request);
  }

  private boolean isValidPath(Request request) {
    if (request.isParams()) {
      return hasPath(request.getParamsRoute());
    }
    return hasPath(request.getRoute());
  }

  private boolean hasPath (String path) {
    String standardRoute ="GET " + path;
    return this.routes.containsKey(standardRoute);
  }

  private boolean checkMethod(Request request) {
    String route = request.getRequest();
    return this.routes.containsKey(route);
  }
}

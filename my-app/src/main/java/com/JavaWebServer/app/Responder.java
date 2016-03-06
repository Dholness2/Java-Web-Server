package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Responder {

  private static final String NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";
  private static final String NOT_FOUND = "HTTP/1.1 404 Not Found"; 
  private static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request";

  private Map <String, ArrayList<String>> routeDirectory;
  private Map <String, RestMethod> routes;

  public Responder(Map<String, ArrayList<String>> routeDirectory, Map<String, RestMethod> routes) {
    this.routeDirectory = routeDirectory;
    this.routes = routes;
  }

  public byte [] getResponse(Request request) {
    if (request.validRequest()){
      return handleValidRequest(request);
    } else {
      return BAD_REQUEST.getBytes();
    }
  }

  private byte[] handleValidRequest(Request request) {
    if (validRoute(request)) {
      return handleValidRoute(request);
    }else{
      return NOT_FOUND.getBytes();
    }
  }

  private byte[] handleValidRoute(Request request) {
    if(checkMethod(request)) {
      return getMessage(request);
    } else {
      return (NOT_ALLOWED + "/n" + "put post").getBytes();
    }
  }

  private byte [] getMessage(Request request){
    String message = request.getRequest();
    RestMethod currentRoute = routes.get(message);
    return currentRoute.handleRequest(request);
  }

  private boolean checkRoute(Request request) {
   if (request.isParams()) {
    return hasRoute(request.getParamsRoute());
   }
   return hasRoute(request.getRoute());
  }

  private boolean hasRoute(String route) {
    return routeDirectory.containsKey(route);
  }

  private boolean checkMethod(Request request) {
    String route = request.getRoute();
    String method = request.getMethod();
    return routeDirectory.get(route).contains(method);
  }
}

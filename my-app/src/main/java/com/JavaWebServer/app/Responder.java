package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.Map; 
import java.util.HashMap;

public class Responder {
  private Map <String, ArrayList<String>> routeDirectory;
  private final String NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";
  private final String NOT_FOUND = "HTTP/1.1 404 not found"; 
  private Map <String, RestMethod> routes;

  public Responder(Map routeDirectory, Map routes) {
    this.routeDirectory = routeDirectory;
    this.routes = routes;
  }

  public byte [] getResponse(Request request) {
    return getResponseHeader(request);
  }

  private byte [] getResponseHeader(Request request) {
    System.out.println("checking....");
    if (checkRoute(request) == true) {
      if(true == checkMethod(request)) {
        return getMessage(request);
      } else {
        return (NOT_ALLOWED +"/n"+ "put post").getBytes();
      }
    }else{
      return NOT_FOUND.getBytes();
    }
  }

  private byte [] getMessage(Request request){
    String message = request.getRequest(); 
    RestMethod currentRoute = routes.get(message);
    return currentRoute.handleRequest(request);
  }

  private boolean checkRoute(Request request) {
    return routeDirectory.containsKey(request.getRoute());
  }

  private boolean checkMethod(Request request) {
    boolean results = routeDirectory.get(request.getRoute()).contains(request.getMethod());
    return (true  == results);
  }
}

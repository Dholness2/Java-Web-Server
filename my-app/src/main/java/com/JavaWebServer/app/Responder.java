package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Responder {
  private HashMap<String, ArrayList<String>> routes;
  private final String NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";
  private final String NOT_FOUND = "HTTP/1.1 404 not found"; 
  private HashMap <String, RestMethod> methods;

  public Responder(HashMap currentRoutes, HashMap methods) {
    this.routes = currentRoutes;
    this.methods = methods;
  }

  public String getResponse(Request request) {
    return getResponseHeader(request);
  }

  private String getResponseHeader(Request request) {
    System.out.println("checking....");
    if (checkRoute(request) == true) {
      if(true == checkMethod(request)) {
        return getMessage(request);
      } else {
        return NOT_ALLOWED +"/n"+ "put post";
      }
    }else{
      return NOT_FOUND;
    }
  }

  private String getMessage(Request request){
    String message = request.getRequest(); 
    RestMethod currentMethod = methods.get(message);
    return currentMethod.handleRequest(request);
  }

  private boolean checkRoute(Request request) {
    return routes.containsKey(request.getRoute());
  }

  private boolean checkMethod(Request request) {
    boolean results = routes.get(request.getRoute()).contains(request.getMethod());
    System.out.println(request.getMethod() + results);
    return (true  == results);
  }
}

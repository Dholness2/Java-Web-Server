package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.Map; 
import java.util.HashMap;

public class Responder {
  private Map <String, ArrayList<String>> routeDirectory;
  private final String NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";
  private final String NOT_FOUND = "HTTP/1.1 404 not found"; 
  private final String BAD_REQUEST = "HTTP/1.1 400 Bad Request";
  private Map <String, RestMethod> routes;

  public Responder(Map routeDirectory, Map routes) {
    this.routeDirectory = routeDirectory;
    this.routes = routes;
  }

  public byte [] getResponse(Request request) {
    if (request.validRequest()){
      if (checkRoute(request) == true) {
        if(true == checkMethod(request)) {
          return getMessage(request);
        } else {
          return (NOT_ALLOWED +"/n"+ "put post").getBytes();
        }
      }else{
        return NOT_FOUND.getBytes();
      }
    } else {
      return BAD_REQUEST.getBytes();
    }
  }

  private byte [] getMessage(Request request){
    String message = request.getRequest();
    RestMethod currentRoute = routes.get(message);
    return currentRoute.handleRequest(request);
  }

  private boolean checkRoute(Request request) {
   if (request.isParams()) {
     System.out.println("got params");
    return hasRoute(request.getParamsRoute());
   }
   System.out.println("not catching it");
   return hasRoute(request.getRoute());
  }

  private boolean hasRoute(String route) {
    return routeDirectory.containsKey(route);
  }

  private boolean checkMethod(Request request) {
    String route = request.getRoute();
    String method = request.getMethod();
    System.out.println(route +""+ method);
    boolean results = routeDirectory.get(route).contains(method);
    return results;
  }
}

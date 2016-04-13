package com.javawebserver.app;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;

import java.util.Map;
import java.util.HashMap;

public class Responder {
  private static final String NOT_ALLOWED_CODE = "405";
  private static final String NOT_FOUND_CODE = "404";
  private static final String BAD_REQUEST_CODE = "400";
  private static final String CRLF = System.getProperty("line.separator");

  private Map <String, Response> routes;
  private ResponseBuilder responseBuilder;

  public Responder(Map <String, Response> routes, ResponseBuilder responseBuilder) {
    this.routes = routes;
    this.responseBuilder = responseBuilder;
  }

  public byte[] getResponse(Request request) {
    if (request.validRequest()){
      return handleValidRequest(request);
    } else {
      return this.responseBuilder.getStatus(BAD_REQUEST_CODE);
    }
  }

  private byte[] handleValidRequest(Request request) {
    if (isValidPath(request)) {
      return handleValidRoute(request);
    }else{
      return this.responseBuilder.getStatus(NOT_FOUND_CODE);
    }
  }

  private byte[] handleValidRoute(Request request) {
    if(checkMethod(request)) {
      return getMessage(request);
    } else {
      return this.responseBuilder.getStatus(NOT_ALLOWED_CODE);
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

  private boolean hasPath(String path) {
    String routesKeys = this.routes.keySet().toString();
    return routesKeys.contains(path);
  }

  private boolean checkMethod(Request request) {
    String route = request.getRequest();
    return this.routes.containsKey(route);
  }
}

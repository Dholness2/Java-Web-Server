package com.JavaWebServer.app;

import java.util.Map;
import java.util.HashMap;

public class RequestParser implements Parser {
  private String  METHODKEY = "method";
  private String   ROUTEKEY = "route";

  private int METHODINDEX = 0;
  private int ROUTEINDEX = 0;

  private String []  methods;
  private  HashMap<String,String> results = new HashMap();

  public RequestParser(String [] methods) { 
    this.methods = methods;
  }

  public Map parse(String [] request){
    return setPairs(request);
  }

  public Map setPairs(String [] requestComponets) {
    this.results.put(METHODKEY, requestComponets[METHODINDEX]);
    this.results.put(ROUTEKEY, requestComponets[ROUTEINDEX]); 
    return this.results;
  }
 }

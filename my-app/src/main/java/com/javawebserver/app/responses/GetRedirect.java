package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Request;

public class GetRedirect implements Response {

private StatusCodes status;
private int port;
private String serverName;
private String locationHeader = "Location: ";
private String CRLF = System.getProperty("line.separator");
private String seperator = "/";

  public GetRedirect(StatusCodes status, int port, String serverName) {
    this.status = status;
    this.port = port;
    this.serverName = serverName;
  }

  public byte [] handleRequest(Request request) {
    return (status.FOUND +CRLF+ getLocation()).getBytes();
  }

  private String  getLocation() {
    return (this.locationHeader+this.serverName + this.port +this.seperator);
  }
}

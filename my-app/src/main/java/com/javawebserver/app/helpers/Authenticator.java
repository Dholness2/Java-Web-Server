package com.javawebserver.app.helpers;

import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;
import java.util.Base64;

import java.io.IOException;
import java.io.File;

public class Authenticator {
  private static final String CREDENTIAL_PATH = System.getProperty("user.dir")+"/auth.txt";
  private static final String CRLF = System.getProperty("line.separator");
  private static final String AUTHORIZATION_HEADER = "Authorization: Basic ";

  public static boolean canAuthenticate (Request request) {
    if (hasValidHeader(request)) {
       ArrayList<String> validCredentials = getValidCredentials();
       return validCredentials.contains(decodeCredential(getCredential(request)));
    }
    return false;
  }

  private static ArrayList<String> getValidCredentials() {
    ArrayList<String> credentials = new ArrayList();
    try {
      credentials = new ArrayList<String>(Files.readAllLines(Paths.get(CREDENTIAL_PATH),Charset.forName("utf-8")));
    } catch (IOException e) {
      ExceptionLogger.logException("unable to read Authentication file" + e);
    }
    return credentials;
  }

  private static boolean hasValidHeader(Request request) {
    return ((request.getHeaders() != null) && (request.getHeaders().contains(AUTHORIZATION_HEADER)));
  }

  private static String getCredential(Request request) {
    String header = request.getHeaders().split(AUTHORIZATION_HEADER)[1];
    return header.substring(0, header.indexOf(CRLF));
  }

  private static String decodeCredential(String encoded) {
     byte[] decoded = Base64.getDecoder().decode(encoded);
     return new String (decoded);
  }
}

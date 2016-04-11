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
  private static String credentialsPath = System.getProperty("user.dir")+"/auth.txt";
  private static final String CRLF = System.getProperty("line.separator");
  private static final String credentialHeader = "Authorization: Basic ";

  public static boolean canAuthenticate (Request request) {
    if (hasValidHeader(request)){
      ArrayList<String> validCredentials = getValidCredentials();
      return validCredentials.contains(decodeCredential(getCredential(request)));
    }
    return false;
  }

  private static ArrayList<String> getValidCredentials() {
    ArrayList<String> credentials = new ArrayList();
    try{
      credentials = new ArrayList<String>(Files.readAllLines(Paths.get(credentialsPath),Charset.forName("utf-8")));
    }catch (IOException e) {
      ExceptionLogger.logException("unable to read Authentication file" + e);
    }
    return credentials;
  }

  private static boolean hasValidHeader(Request request) {
    return ((request.getHeaders() != null) && (request.getHeaders().contains(credentialHeader)));
  }

  private static String getCredential(Request request){
    String header = request.getHeaders().split(credentialHeader)[1];
    String credentials = header.substring(0, header.indexOf(CRLF));
    return credentials;
  }

  private static String decodeCredential(String encoded) {
    return new String (Base64.getDecoder().decode(encoded));
  }
}

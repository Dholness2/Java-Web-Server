package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.net.*;
import java.io.*;

public class ClientWorkerTest {
  
  @test
  public void respondsTOClients Request() throws Exception {
    Socket ClientTestSocket = new Socket("localHost", 9999)
    ClientWorker testWorker = New ClientWorker(CleintTestSocket, "local host");
    new.thread(testWorker).Start();
    PrintWriter outPut = new PrintWriter(ClienttestSocket.getOutputStream(),true);
    outPut.print("GET / http/1.1");
    BufferReader reponse = new BufferedReader(new inputStreamReader(clientTestScoket.getInputStream()); 
    assertEquals("200", response.readLine());
}

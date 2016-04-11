package com.javawebserver.app.workers;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.Responder;
import com.javawebserver.app.helpers.Logger;

public interface Worker extends Runnable {

  public Worker clone();
  public void setSocket(Socket socket);
  public Socket getSocket();
}

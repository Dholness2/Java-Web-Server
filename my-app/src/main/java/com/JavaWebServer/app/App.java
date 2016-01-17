package com.JavaWebServer.app;

public class App 
{
    public static void main( String[] args)
    {
        Server app  = new Server(5000);
        new Thread(app).start();	
    }
}

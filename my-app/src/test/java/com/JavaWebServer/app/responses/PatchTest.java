package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.Patch;

import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.helpers.FileEditor;

import com.JavaWebServer.app.encoders.Encoder;
import com.JavaWebServer.app.encoders.SHA1Encoder;

public class PatchTest {
  private  Request testRequest = new Request();
  private StatusCodes codes = new StatusCodes();
  private String directory = System.getProperty("user.dir")+"/public";
  private String fileName = "/patch-content.txt";
  private String CRLF = System.getProperty("line.separator");

  private void setRequest(String method ,String route,int length, String etag, String body) {
   testRequest.setMessage(method+route+"HTTP/1.1"+CRLF);
   testRequest.setHeaders("If-Match: "+etag+ CRLF +"Content-Length: "+ length);
   testRequest.setBody(body);
  }

  public  class FileEditorMock extends FileEditor {
    public boolean hasEditedFile = false;

    public FileEditorMock () {
    }

    @Override
    public void edit(String path, String post) {
      this.hasEditedFile = true;
    }

    public boolean fileChanged() {
      return this.hasEditedFile;
    }
  }

  @Test
  public void HandleRequestPatchTest() {
    setRequest("PATCH","/patch-content.txt",15,"dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec","patched content");
    FileEditorMock editorMock = new FileEditorMock();
    Encoder encoder = new SHA1Encoder();
    Response testPatch = new Patch(codes,fileName,directory,editorMock,encoder);
    String response = new String (testPatch.handleRequest(testRequest));
    assertEquals(true,editorMock.hasEditedFile);
    assertEquals(codes.NO_CONTENT, response);
  }

  @Test
  public void HandleRequestPatchFailTest() {
    setRequest("PATCH","/patch-content.txt",15,"5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0","patched content");
    FileEditorMock editorMock = new FileEditorMock();
    Encoder encoder = new SHA1Encoder();
    Response testPatch = new Patch(codes,fileName,directory,editorMock,encoder);
    String response = new String (testPatch.handleRequest(testRequest));
    assertEquals(false,editorMock.hasEditedFile);
    assertEquals(codes.NOT_FOUND, response);
  }
}

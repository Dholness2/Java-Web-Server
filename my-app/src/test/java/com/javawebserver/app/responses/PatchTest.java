package com.javawebserver.app.responses;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Patch;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

import com.javawebserver.app.encoders.Encoder;
import com.javawebserver.app.encoders.SHA1Encoder;

public class PatchTest {
  private Request testRequest = new Request();
  private ResponseBuilder testResponse= new HttpResponseBuilder();
  private String directory = System.getProperty("user.dir")+"/public";
  private String fileName = "/patch-content.txt";
  private static final String CRLF = System.getProperty("line.separator");

  private void setRequest(String method, String route, int length, String etag, String body) {
    testRequest.setMessage(method + route + "HTTP/1.1" + CRLF);
    testRequest.setHeaders("If-Match: "+ etag + CRLF + "Content-Length: "+ length);
    testRequest.setBody(body);
  }

  public class FileEditorMock extends FileEditor {
    public boolean hasEditedFile = false;

    public FileEditorMock () {}

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
    Response testPatch = new Patch(testResponse,fileName,directory,editorMock,encoder);
    String response = new String (testPatch.handleRequest(testRequest));
    assertEquals(true,editorMock.hasEditedFile);
    assertEquals("HTTP/1.1 204 No Content", response);
  }

  @Test
  public void HandleRequestPatchFailTest() {
    setRequest("PATCH","/patch-content.txt",15,"5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0","patched content");
    FileEditorMock editorMock = new FileEditorMock();
    Encoder encoder = new SHA1Encoder();
    Response testPatch = new Patch(testResponse,fileName,directory,editorMock,encoder);
    String response = new String (testPatch.handleRequest(testRequest));
    assertEquals(false,editorMock.hasEditedFile);
    assertEquals("HTTP/1.1 404 Not Found", response);
  }
}

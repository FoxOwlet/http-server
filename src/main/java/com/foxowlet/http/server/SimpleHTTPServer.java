package com.foxowlet.http.server;

import com.foxowlet.http.core.ResourceFileWebPage;
import com.foxowlet.http.core.WebResource;
import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

public class SimpleHTTPServer extends AbstractHTTPServer {

    @Override
    protected void handleRequest(HttpRequest request, HttpResponse.Builder responseBuilder) {
        if (request.getAddress().equals("/")) {
            WebResource resource = new ResourceFileWebPage("index.html");
            responseBuilder
                    .setResponseCode(200)
                    .setReason("OK")
                    .setBody(resource);
        } else {
            responseBuilder
                    .setResponseCode(404)
                    .setReason("Not Found");
        }
    }
}

package com.foxowlet.http.server;

import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

public class SimpleHTTPServer extends AbstractHTTPServer {

    @Override
    protected HttpResponse handleRequest(HttpRequest request) {
        HttpResponse.Builder responseBuilder = HttpResponse.builder()
                .setVersion(request.getVersion());
        if (request.getAddress().equals("/")) {
            String htmlPage = """
                        <html>
                          <head>
                            <title>Test web page</title>
                          </head>
                          <body>
                            <h1>Test web page</h1>
                            <br>
                            This is some content.
                            <br>
                            This is <b>bold</b> text.
                          </body>
                        </html>
                        """;
            responseBuilder
                    .setResponseCode(200)
                    .setReason("OK")
                    .setHeader("Content-Type", "text/html; charset=utf-8")
                    .setBody(htmlPage);
        } else {
            responseBuilder
                    .setResponseCode(404)
                    .setReason("Not Found");
        }
        return responseBuilder.build();
    }
}

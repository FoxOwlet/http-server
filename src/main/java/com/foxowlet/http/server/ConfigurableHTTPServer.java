package com.foxowlet.http.server;

import com.foxowlet.http.core.WebResource;
import com.foxowlet.http.core.WebResourceDispatcher;
import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

import java.net.URI;
import java.util.Optional;

public class ConfigurableHTTPServer extends AbstractHTTPServer {
    private final WebResourceDispatcher dispatcher;

    public ConfigurableHTTPServer(WebResourceDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public ConfigurableHTTPServer(WebResourceDispatcher dispatcher, int port, int threads) {
        super(port, threads);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void handleRequest(HttpRequest request, HttpResponse.Builder responseBuilder) {
        Optional<WebResource> resource = dispatcher.dispatch(URI.create(request.getAddress()));
        if (resource.isPresent()) {
            responseBuilder
                    .setResponseCode(200)
                    .setReason("OK")
                    .setBody(resource.get());
        } else {
            responseBuilder
                    .setResponseCode(404)
                    .setReason("Not Found");
        }
    }
}

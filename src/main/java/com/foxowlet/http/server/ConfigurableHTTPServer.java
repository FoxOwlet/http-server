package com.foxowlet.http.server;

import com.foxowlet.http.core.WebResource;
import com.foxowlet.http.core.WebResourceDispatcher;
import com.foxowlet.http.core.WebResourceDispatcherFactory;
import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

import java.net.URI;
import java.util.Optional;

public class ConfigurableHTTPServer extends AbstractHTTPServer {
    private final WebResourceDispatcher dispatcher;

    public ConfigurableHTTPServer(ServerConfiguration config) {
        super(Integer.parseInt(config.getProperty("server.port")),
                Integer.parseInt(config.getProperty("server.num-threads")));
        dispatcher = new WebResourceDispatcherFactory(config).configureDispatcher();
        setServerName(config.getProperty("server.name"));
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

package com.foxowlet.http.core;

import com.foxowlet.http.protocol.HttpRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeWebResourceDispatcher implements WebResourceDispatcher {
    private final List<WebResourceDispatcher> dispatchers = new ArrayList<>();

    public CompositeWebResourceDispatcher registerDispatcher(WebResourceDispatcher dispatcher) {
        dispatchers.add(dispatcher);
        return this;
    }

    @Override
    public Optional<WebResource> dispatch(HttpRequest request) {
        for (WebResourceDispatcher dispatcher : dispatchers) {
            Optional<WebResource> resource = dispatcher.dispatch(request);
            if (resource.isPresent()) {
                return resource;
            }
        }
        return Optional.empty();
    }
}

package com.foxowlet.http.core;

import com.foxowlet.http.protocol.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StaticWebResourceDispatcher implements WebResourceDispatcher {
    private final Map<String, WebResource> dispathMap = new HashMap<>();

    public StaticWebResourceDispatcher addMapping(String path, WebResource resource) {
        dispathMap.put(path, resource);
        return this;
    }

    @Override
    public Optional<WebResource> dispatch(HttpRequest request) {
        return Optional.ofNullable(dispathMap.get(request.getPath()));
    }
}

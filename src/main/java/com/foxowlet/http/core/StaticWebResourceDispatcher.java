package com.foxowlet.http.core;

import java.net.URI;
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
    public Optional<WebResource> dispatch(URI uri) {
        return Optional.ofNullable(dispathMap.get(uri.getPath()));
    }
}

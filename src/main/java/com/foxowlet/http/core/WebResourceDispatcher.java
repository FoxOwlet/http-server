package com.foxowlet.http.core;

import com.foxowlet.http.protocol.HttpRequest;

import java.util.Optional;

public interface WebResourceDispatcher {
    Optional<WebResource> dispatch(HttpRequest request);
}

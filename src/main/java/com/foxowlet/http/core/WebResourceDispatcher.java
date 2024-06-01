package com.foxowlet.http.core;

import java.net.URI;
import java.util.Optional;

public interface WebResourceDispatcher {
    Optional<WebResource> dispatch(URI path);
}

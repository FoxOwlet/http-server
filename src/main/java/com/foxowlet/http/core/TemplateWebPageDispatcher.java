package com.foxowlet.http.core;

import com.foxowlet.http.protocol.HttpRequest;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TemplateWebPageDispatcher implements WebResourceDispatcher {
    private final Path baseDirectory;

    public TemplateWebPageDispatcher(Path baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public Optional<WebResource> dispatch(HttpRequest request) {
        Path filePath = baseDirectory.resolve(request.getPath().substring(1));
        if (filePath.toFile().exists()) {
            System.err.printf("Dispatching %s: found%n", filePath);
            return Optional.of(new TemplateWebPage(filePath.toString(), request.getParams()));
        }
        System.err.printf("Dispatching %s: not found%n", filePath);
        return Optional.empty();
    }
}

package com.foxowlet.http.core;

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
    public Optional<WebResource> dispatch(URI uri) {
        Path filePath = baseDirectory.resolve(uri.getPath().substring(1));
        Map<String, String> params = extractQueryParams(uri);
        if (filePath.toFile().exists()) {
            System.err.printf("Dispatching %s: found%n", filePath);
            return Optional.of(new TemplateWebPage(filePath.toString(), params));
        }
        System.err.printf("Dispatching %s: not found%n", filePath);
        return Optional.empty();
    }

    private static Map<String, String> extractQueryParams(URI uri) {
        Map<String, String> params = new HashMap<>();
        String query = uri.getQuery();
        if (query != null) {
            for (String paramValuePair : query.split("&")) {
                String[] parts = paramValuePair.split("=");
                if (parts.length != 2) {
                    throw new IllegalStateException("Invalid params: " + query);
                }
                params.put(parts[0], parts[1]);
            }
        }
        return params;
    }
}

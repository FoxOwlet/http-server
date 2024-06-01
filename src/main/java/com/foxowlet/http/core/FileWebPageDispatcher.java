package com.foxowlet.http.core;

import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

public class FileWebPageDispatcher implements WebResourceDispatcher {
    private final Path baseDirectory;

    public FileWebPageDispatcher(Path baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public Optional<WebResource> dispatch(URI uri) {
        Path filePath = baseDirectory.resolve(uri.getPath().substring(1));
        if (filePath.toFile().exists()) {
            System.err.printf("Dispatching %s: found%n", filePath);
            return Optional.of(new FileWebPage(filePath.toString()));
        }
        System.err.printf("Dispatching %s: not found%n", filePath);
        return Optional.empty();
    }
}

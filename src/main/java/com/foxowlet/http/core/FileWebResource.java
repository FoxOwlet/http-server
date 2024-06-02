package com.foxowlet.http.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWebResource implements WebResource {
    private final Path filePath;

    public FileWebResource(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public String getContentType() {
        try {
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] getBytes() {
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

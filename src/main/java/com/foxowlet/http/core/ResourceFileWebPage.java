package com.foxowlet.http.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceFileWebPage extends AbstractWebPage {
    private final String filePath;

    public ResourceFileWebPage(String filePath) {
        super();
        this.filePath = filePath;
    }

    public ResourceFileWebPage(String filePath, Charset charset) {
        super(charset);
        this.filePath = filePath;
    }

    @Override
    public byte[] getBytes() {
        try {
            return Files.readAllBytes(Path.of(getClass().getClassLoader().getResource(filePath).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}

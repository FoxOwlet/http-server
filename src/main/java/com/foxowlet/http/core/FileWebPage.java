package com.foxowlet.http.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWebPage extends AbstractWebPage {
    private final String filePath;

    public FileWebPage(String filePath) {
        super();
        this.filePath = filePath;
    }

    public FileWebPage(String filePath, Charset charset) {
        super(charset);
        this.filePath = filePath;
    }

    @Override
    public byte[] getBytes() {
        try {
            return Files.readAllBytes(Path.of(filePath));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

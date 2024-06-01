package com.foxowlet.http.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class AbstractWebPage implements WebResource {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Charset charset;

    protected AbstractWebPage() {
        this(DEFAULT_CHARSET);
    }

    protected AbstractWebPage(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String getContentType() {
        return "text/html; charset=" + charset.name();
    }

    protected Charset getCharset() {
        return charset;
    }
}

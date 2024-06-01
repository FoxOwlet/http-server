package com.foxowlet.http.core;

import java.nio.charset.Charset;

public class InlineWebPage extends AbstractWebPage {
    private final String content;

    public InlineWebPage(String content) {
        super();
        this.content = content;
    }

    public InlineWebPage(String content, Charset charset) {
        super(charset);
        this.content = content;
    }

    @Override
    public byte[] getBytes() {
        return content.getBytes(getCharset());
    }
}

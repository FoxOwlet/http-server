package com.foxowlet.http;

import com.foxowlet.http.core.*;
import com.foxowlet.http.server.ConfigurableHTTPServer;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        WebResourceDispatcher dispatcher = new CompositeWebResourceDispatcher()
                .registerDispatcher(new StaticWebResourceDispatcher()
                        .addMapping("/", new FileWebPage("static/index.html")))
                .registerDispatcher(new FileWebPageDispatcher(Path.of("static/")))
                .registerDispatcher(new TemplateWebPageDispatcher(Path.of("templates/")));
        new ConfigurableHTTPServer(dispatcher).start();
    }
}

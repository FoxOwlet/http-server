package com.foxowlet.http.core;

import com.foxowlet.http.server.ServerConfiguration;

import java.nio.file.Path;
import java.util.function.Function;

public class WebResourceDispatcherFactory {
    private final ServerConfiguration config;

    public WebResourceDispatcherFactory(ServerConfiguration config) {
        this.config = config;
    }

    public WebResourceDispatcher configureDispatcher() {
        CompositeWebResourceDispatcher dispatcher = new CompositeWebResourceDispatcher();
        addIndexDispatcher(dispatcher);
        addDirectoryDispatcher(dispatcher, "server.web.static-dir", FileWebPageDispatcher::new);
        addDirectoryDispatcher(dispatcher, "server.web.templates-dir", TemplateWebPageDispatcher::new);
        return dispatcher;
    }

    private void addIndexDispatcher(CompositeWebResourceDispatcher dispatcher) {
        String index = config.getProperty("server.web.index");
        if (index != null) {
            StaticWebResourceDispatcher staticDispatcher = new StaticWebResourceDispatcher();
            String dir = config.getProperty("server.web.static-dir", "");
            staticDispatcher.addMapping("/", new FileWebPage(dir + index));
            dispatcher.registerDispatcher(staticDispatcher);
        }
    }

    private void addDirectoryDispatcher(CompositeWebResourceDispatcher dispatcher,
                                        String property,
                                        Function<Path, WebResourceDispatcher> constructor) {
        String directory = config.getProperty(property);
        if (directory != null) {
            dispatcher.registerDispatcher(constructor.apply(Path.of(directory)));
        }
    }
}

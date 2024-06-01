package com.foxowlet.http;

import com.foxowlet.http.server.ConfigurableHTTPServer;
import com.foxowlet.http.server.ServerConfiguration;

public class Main {
    public static void main(String[] args) {
        ServerConfiguration config = new ServerConfiguration("server.properties");
        new ConfigurableHTTPServer(config).start();
    }
}

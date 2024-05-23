package com.foxowlet.http.server;

import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

import java.io.*;

public abstract class AbstractHTTPServer extends AbstractTCPServer {
    private static final int DEFAULT_PORT = 80;
    private static final int DEFAULT_NUM_THREADS = 10;
    private static final int DEFAULT_TIMEOUT = 60_000;

    public AbstractHTTPServer() {
        this(DEFAULT_PORT, DEFAULT_NUM_THREADS);
    }

    protected AbstractHTTPServer(int port, int threads) {
        this(port, threads, DEFAULT_TIMEOUT);
    }

    protected AbstractHTTPServer(int port, int threads, int timeout) {
        super(port, threads);
        setReadTimeout(timeout);
    }

    @Override
    protected void handleConnection(InputStream inputStream, OutputStream outputStream) throws IOException {
        while (true) {
            HttpRequest request = HttpRequest.parse(inputStream);
            HttpResponse response = handleRequest(request);
            response.write(outputStream);
        }
    }

    protected abstract HttpResponse handleRequest(HttpRequest request);
}

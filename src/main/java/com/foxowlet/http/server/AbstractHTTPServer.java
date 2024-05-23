package com.foxowlet.http.server;

import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

import java.io.*;

public abstract class AbstractHTTPServer extends AbstractTCPServer {
    private static final int DEFAULT_PORT = 80;
    private static final int DEFAULT_NUM_THREADS = 10;

    public AbstractHTTPServer() {
        this(DEFAULT_PORT, DEFAULT_NUM_THREADS);
    }

    protected AbstractHTTPServer(int port, int threads) {
        super(port, threads);
    }

    @Override
    protected void handleConnection(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            bufferedInputStream.mark(1);
            while (bufferedInputStream.read() != -1) {
                bufferedInputStream.reset();
                HttpRequest request = HttpRequest.parse(bufferedInputStream);
                bufferedInputStream.mark(1);
                HttpResponse response = handleRequest(request);
                response.write(outputStream);
            }
        }
    }

    protected abstract HttpResponse handleRequest(HttpRequest request);
}

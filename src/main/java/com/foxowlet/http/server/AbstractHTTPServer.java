package com.foxowlet.http.server;

import com.foxowlet.http.protocol.HttpRequest;
import com.foxowlet.http.protocol.HttpResponse;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractHTTPServer extends AbstractTCPServer {
    private static final int DEFAULT_PORT = 80;
    private static final int DEFAULT_NUM_THREADS = 10;
    private static final DateTimeFormatter HTTP_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            // Mon, 23 May 2005 22:38:34 GMT
            "EE, dd MMM yyyy HH:mm:ss z");

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
                System.err.println(request);
                bufferedInputStream.mark(1);
                HttpResponse.Builder responseBuilder = HttpResponse.builder();
                preprocessResponse(responseBuilder, request);
                processRequest(request, responseBuilder);
                postprocessResponse(responseBuilder, request);
                responseBuilder.build().write(outputStream);
            }
        }
    }

    private void preprocessResponse(HttpResponse.Builder responseBuilder, HttpRequest request) {
        responseBuilder.setVersion(request.getVersion())
                .setHeader("Server", "Simple HTTP server");
    }

    private void processRequest(HttpRequest request, HttpResponse.Builder responseBuilder) {
        try {
            handleRequest(request, responseBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            responseBuilder.setResponseCode(500)
                    .setReason("Internal error");
        }
    }

    private void postprocessResponse(HttpResponse.Builder responseBuilder, HttpRequest request) {
        ZonedDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC")).atZone(ZoneId.of("GMT"));
        responseBuilder.setHeader("Date", HTTP_DATE_FORMATTER.format(currentDateTime));
    }

    protected abstract void handleRequest(HttpRequest request, HttpResponse.Builder responseBuilder);
}

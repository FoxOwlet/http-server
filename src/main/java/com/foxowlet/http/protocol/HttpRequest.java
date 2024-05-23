package com.foxowlet.http.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpRequest {
    private final HttpMethod method;
    private final String address;
    private final String version;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpRequest(HttpMethod method, String address, String version, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.address = address;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getAddress() {
        return address;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Optional<byte[]> getBody() {
        return Optional.ofNullable(body);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        joiner.add("%s %s %s".formatted(method, address, version));
        headers.forEach((key, value) -> {
            joiner.add("%s: %s".formatted(key, value));
        });
        if (body != null) {
            joiner.add(new String(body, StandardCharsets.UTF_8));
            joiner.add(Arrays.toString(body));
        }
        return joiner.toString();
    }

    public static HttpRequest parse(InputStream stream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String startLine = reader.readLine().trim();
        String[] parts = startLine.split(" ");
        HttpMethod method = HttpMethod.valueOf(parts[0]);
        String address = parts[1];
        String version = parts[2];
        Map<String, String> headers = new HashMap<>();
        String header;
        while (true) {
            header = reader.readLine().trim();
            if (header.isBlank()) {
                break;
            }
            String[] headerParts = header.split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }
        byte[] body = null;
        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            int length = Integer.parseInt(contentLength);
            body = new byte[length];
            int bytesRead = stream.read(body);
            if (bytesRead != length) {
                throw new IllegalStateException("Can't properly read request body, expected %d bytes, got %d bytes"
                        .formatted(length, bytesRead));
            }
        }
        return new HttpRequest(method, address, version, headers, body);
    }
}

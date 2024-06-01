package com.foxowlet.http.protocol;

import com.foxowlet.http.core.WebResource;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final String version;
    private final int responseCode;
    private final String reason;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(String version, int responseCode, String reason, Map<String, String> headers, byte[] body) {
        this.version = version;
        this.responseCode = responseCode;
        this.reason = reason;
        this.headers = headers;
        this.body = body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void write(OutputStream stream) throws IOException {
        String statusLine = "%s %d %s\r\n".formatted(version, responseCode, reason);
        stream.write(statusLine.getBytes(StandardCharsets.UTF_8));
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String header = "%s: %s\r\n".formatted(entry.getKey(), entry.getValue());
            stream.write(header.getBytes(StandardCharsets.UTF_8));
        }
        stream.write("\r\n".getBytes(StandardCharsets.UTF_8));
        if (body != null) {
            stream.write(body);
        }
        stream.flush();
    }

    public static class Builder {
        private String version = "HTTP/1.1";
        private int responseCode;
        private String reason;
        private final Map<String, String> headers = new HashMap<>();
        private byte[] body = null;

        private Builder() {
            setHeader("Content-Length", "0");
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setResponseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setBody(byte[] body) {
            setHeader("Content-Length", String.valueOf(body.length));
            this.body = body;
            return this;
        }

        public Builder setBody(WebResource resource) {
            setHeader("Content-Type", resource.getContentType());
            return setBody(resource.getBytes());
        }

        public Builder setBody(String body) {
            return setBody(body.getBytes(StandardCharsets.UTF_8));
        }

        public Builder setHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public HttpResponse build() {
            if (responseCode == 0) {
                throw new IllegalStateException("Response code must be set");
            }
            if (reason == null) {
                throw new IllegalStateException("Reason must be set");
            }
            return new HttpResponse(version, responseCode, reason, headers, body);
        }
    }
}

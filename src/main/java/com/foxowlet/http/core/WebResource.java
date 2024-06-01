package com.foxowlet.http.core;

public interface WebResource {
    String getContentType();

    byte[] getBytes();
}

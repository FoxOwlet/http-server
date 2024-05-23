package com.foxowlet.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractTCPServer {
    private final int port;
    private final int threads;
    private int readTimeout;

    protected AbstractTCPServer(int port, int threads) {
        this.port = port;
        this.threads = threads;
        this.readTimeout = 0;
    }

    public int getPort() {
        return port;
    }

    public int getThreadsNumber() {
        return threads;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int timeout) {
        readTimeout = timeout;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> handleConnection(clientSocket));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can't start server", e);
        }
    }

    private void handleConnection(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            clientSocket.setSoTimeout(readTimeout);
            handleConnection(inputStream, outputStream);
        } catch (SocketTimeoutException e) {
            System.err.println("Read timeout for connection from client " + clientSocket.getRemoteSocketAddress());
        } catch (IOException e) {
            throw new IllegalStateException("Can't handle connection from client " + clientSocket.getRemoteSocketAddress(), e);
        }
    }

    protected abstract void handleConnection(InputStream inputStream, OutputStream outputStream) throws IOException;
}

package org.example;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class AutoResponder extends Thread {

    private static final Logger LOGGER = Logger.getLogger(AutoResponder.class);

    private final BufferedReader in;
    private final PrintWriter out;
    private final Socket clientSocket;
    private final ServerSocket serverSocket;

    public AutoResponder(BufferedReader in, PrintWriter out, Socket clientSocket, ServerSocket serverSocket) {
        this.in = in;
        this.out = out;
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg;
                do {
                    msg = in.readLine();
                    if (msg != null) {
                        LOGGER.info("Received message from client: " + msg + ", size " + msg.getBytes(StandardCharsets.UTF_8).length);
                        out.println(msg);
                        out.flush();
                    }
                } while (msg != null);

                LOGGER.info("Client disconnected!");
                out.close();
                clientSocket.close();
                serverSocket.close();
                break;
            } catch (IOException e) {
                LOGGER.error("Unknown error occurred", e);
            }
        }
    }
}

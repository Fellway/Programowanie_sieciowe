package org.example;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Reader extends Thread {

    private final static Logger LOGGER = Logger.getLogger(Reader.class);

    private final BufferedReader in;
    private final Socket clientSocket;


    public Reader(BufferedReader in, Socket clientSocket) {
        this.in = in;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = in.readLine();
                if (message != null) {
                    LOGGER.info("Received message from server: " + message + ", size: " + message.getBytes(StandardCharsets.UTF_8).length);
                } else {
                    LOGGER.error("Connection error.");
                    clientSocket.close();
                    break;
                }
            } catch (IOException e) {
                LOGGER.error("Unknown error on the client side.", e);
            }
        }
    }

}

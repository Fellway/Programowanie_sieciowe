package org.example;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {

    private final static Logger LOGGER = Logger.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Podaj adres hosta.");
            String host = sc.nextLine();
            System.out.println("Podaj numer portu.");
            int port = sc.nextInt();
            clientSocket = new Socket(host, port);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if (clientSocket.isConnected()) {
                LOGGER.info("Successfully connected to the server.");
                Reader reader = new Reader(in, clientSocket);
                Sender sender = new Sender(out);

                reader.start();
                sender.start();
            } else {
                LOGGER.error("Cannot connect to the server. Check hostname and port.");
            }
        } catch (IOException e) {
            LOGGER.error("Cannot connect to the server", e);
        }
    }

}

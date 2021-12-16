package org.example;

import java.io.PrintWriter;
import java.util.Scanner;

public class Sender extends Thread {

    private final PrintWriter out;

    public Sender(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            out.println(msg);
            out.flush();
        }

    }
}

package com.company.proiect;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterNode extends Thread {
    private ServerSocket serverSocket;
    private int replicationNr;

    public MasterNode() throws IOException {
        this.serverSocket = new ServerSocket(5555);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println("hello from server, available commands: ls, cat [path], rm[PATH], crt [path] [content]");
                printStream.flush();
                new Thread(new ClientHandler(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

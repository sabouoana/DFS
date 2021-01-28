package com.company.proiect;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // ia inputstream si outputstream de pe socket
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Attempt to connect to storage node.");

            Socket storageSocket = new Socket("localhost", 5556);
            System.out.println("Connected to storage node");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                System.out.println(inputLine);
                PrintWriter printStream = new PrintWriter(storageSocket.getOutputStream());
                printStream.write(inputLine+"\n");
                printStream.flush();

                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(storageSocket.getInputStream()));
                String result = dataInputStream.readLine();
                out.write(result+"\n");
                out.flush();
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {

        }

    }
}

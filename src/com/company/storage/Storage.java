package com.company.storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Storage extends Thread{
    private ServerSocket serverSocket;
    private String basePath = "/resources";
    public Storage() {
        try {
            serverSocket = new ServerSocket(5556);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true){
            try {
                Socket socket = serverSocket.accept();
                new StorageActionHandler(basePath, socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

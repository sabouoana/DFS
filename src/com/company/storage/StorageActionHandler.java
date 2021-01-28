package com.company.storage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class StorageActionHandler extends Thread {
    public static final String BASE_PATH = "/Users/bfilip/Desktop/Personal/DFS/resources";
    private final Socket socket;

    public StorageActionHandler(String basePath, Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));


            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                try {
                    if (inputLine.startsWith("ls")) {
                        System.out.println("About to list files in path: " + BASE_PATH);
                        File file = new File(BASE_PATH);
                        if (file.list() == null) {
                            System.out.println("Folder is empty");
                            out.write("The folder is empty\n");
                            out.flush();
                        }
                        String filelist = Arrays.stream(Objects.requireNonNull(file.list())).reduce("", (a, b) -> a + " " + b);
                        out.write(filelist + "\n");
                        out.flush();
                        System.out.println("Sent " + filelist);
                    }

                    if (inputLine.startsWith("cat")) {
                        String path = Arrays.asList(inputLine.split(" ")).get(1);
                        System.out.println("About to show content for file " + path);
                        out.write(Files.readAllLines(Paths.get(BASE_PATH + "/" + path)).stream().reduce("", (acumulator, line) -> acumulator + line) + "\n");
                        out.flush();
                    }

                    if (inputLine.startsWith("rm")) {
                        String path = Arrays.asList(inputLine.split(" ")).get(1);
                        System.out.println("About to delete " + BASE_PATH + path);
                        Files.delete(Paths.get(BASE_PATH + "/" + path));
                        out.write("Deleted\n");
                        out.flush();
                    }

                    if (inputLine.startsWith("crt")) {
                        String path = Arrays.asList(inputLine.split(" ")).get(1);
                        String[] splitedWords = inputLine.split(" ");
                        String content = "";
                        int idx = 0;
                        for (String s : splitedWords) {
                            if (idx > 1) {
                                content += s;
                                content += " ";
                            }
                            idx++;
                        }
                        content = content.replace("\\n", System.lineSeparator());

                        Path filePath = Files.createFile(Paths.get(BASE_PATH + "/" + path));


                        FileWriter fileWriter = new FileWriter(BASE_PATH + "/" + path);
                        fileWriter.write(content);
                        fileWriter.flush();
                        out.write("created " + filePath.toString() + "\n");
                        out.flush();
                    }

                    if (inputLine.startsWith("append")) {
                        String[] splittedWords = inputLine.split(" ");
                        String path = splittedWords[1];
                        boolean append = Boolean.valueOf(splittedWords[2]);
                        int idx = 0;
                        String content = "";

                        for (String s : splittedWords) {
                            if (idx > 2) {
                                content += s;
                                content += " ";
                            }
                            idx++;
                        }

                        content = content.replace("\\n", System.lineSeparator());
                        FileWriter fileWriter = new FileWriter(new File(BASE_PATH + "/" + path), append);
                        fileWriter.write(content);
                        fileWriter.flush();
                        out.write("added content to file " + "\n");
                        out.flush();
                    }

                } catch (Exception e) {


                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

package com.company.proiect;

import java.io.IOException;

public class MasterNodeStarter {
    public static void main(String[] args) throws IOException {
        MasterNode masterNode = new MasterNode();
        masterNode.start();
    }
}

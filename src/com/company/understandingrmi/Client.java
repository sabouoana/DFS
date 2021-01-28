package com.company.understandingrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        Multiplier stub = (Multiplier) Naming.lookup("rmi://localhost:5000/sonoo");
        System.out.println(stub.multiply(34, 4));
    }
}

package com.company.understandingrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MyServer {
    public static void main(String[] args) throws MalformedURLException, RemoteException {
        Multiplier stub=new MultiplierRemote();
        LocateRegistry.createRegistry(5000);
        Naming.rebind("rmi://localhost:5000/sonoo",stub);
    }
}

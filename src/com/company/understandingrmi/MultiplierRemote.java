package com.company.understandingrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MultiplierRemote extends UnicastRemoteObject implements Multiplier {
    protected MultiplierRemote() throws RemoteException {
    }

    @Override
    public int multiply(int x, int y) throws RemoteException {
        return x * y;
    }
}

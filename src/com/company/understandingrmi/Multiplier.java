package com.company.understandingrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Multiplier extends Remote {
    int multiply(int x, int y) throws RemoteException;
}

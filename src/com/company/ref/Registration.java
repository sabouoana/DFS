package com.company.ref;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.NotBoundException;

public interface Registration extends Remote  
{
	
	public String[] register(String IP_STORAGE_SERVER, int PORT_STORAGE_SERVER, String[] files, Storage command_stub) throws RemoteException, NotBoundException;
	
}

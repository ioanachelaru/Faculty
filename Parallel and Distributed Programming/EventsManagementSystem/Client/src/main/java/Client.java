import Server.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject {

    private IServer server;

    public Client() throws RemoteException { }

    public void setServer(IServer server) { this.server = server; }

}

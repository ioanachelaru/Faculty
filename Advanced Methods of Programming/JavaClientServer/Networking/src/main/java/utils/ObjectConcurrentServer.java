package utils;

import Services.IServer;
import objectprotocol.ClientObjectWorker;
import java.net.Socket;


public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IServer chatServer;
    public ObjectConcurrentServer(int port, IServer chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ObjectConcurrentServer");
    }

    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker=new ClientObjectWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}

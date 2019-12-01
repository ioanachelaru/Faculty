package objectprotocol;

import Model.Angajat;
import Model.Inscriere;
import Model.Participant;
import Model.ProbaExtins;
import Services.IObserver;
import Services.IServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerObjectProxy implements IServer {
    private String host;
    private int port;

    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;


    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(Angajat user, IObserver client) {
        sendRequest(new LoginRequest(user));
        Response response = readResponse();
        if (response instanceof OkResponse){
            this.client = client;
            return;
        }
    }

    @Override
    public void logout(Angajat user, IObserver client) {
        sendRequest(new LogoutRequest(user));
        Response response=readResponse();
        closeConnection();
    }

    @Override
    public List<Angajat> getAllAngajati() {
        sendRequest(new GetAllAngajatsRequest());
        Response response = readResponse();
        return ((GetAllAngajatsResponse) response).getAngajats();
    }

    @Override
        public List<ProbaExtins> getAllProbaExtins() {
        sendRequest(new GetAllProbaExtinsRequest());
        Response response = readResponse();
        return ((GetAllProbaExtinsResponse) response).getProbaExtins();
    }

    @Override
    public Iterable<Participant> getAllParticipants(String idProba) {
        sendRequest((new GetAllParticipantsByIdRequest(idProba)));
        Response response = readResponse();
        return ((GetAllParticipantsByIdResponse) response).getParticipants();
    }

    @Override
    public Integer getMaxId() {
        sendRequest((new GetMaxIdRequest()));
        Response response = readResponse();
        return ((GetMaxIdResponse) response).getId();
    }

    @Override
    public void addParticipant(Participant participant) {
        sendRequest(new AddParticipantRequest(participant));
        Response response = readResponse();
    }

    @Override
    public void addInscriere(Inscriere inscriere) {
        sendRequest(new AddInscriereRequest(inscriere));
        Response response = readResponse();
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private Response readResponse() {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) throws RemoteException {

        if (update instanceof NotifyResponse){
            client.update(((NotifyResponse) update).getProbe());
            System.out.println("handleNotifyProxy ok");
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}

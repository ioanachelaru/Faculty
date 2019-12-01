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
import java.util.List;

public class ClientObjectWorker implements Runnable, IObserver {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientObjectWorker(IServer server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private void sendResponse(Response response) {
        if (response != null) {
            System.out.println("sending response " + response);
            try {
                synchronized (output){
                    output.writeObject(response);
                    output.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object handleRequest(Request request) {
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            Angajat user = logReq.getUser();
            server.login(user, this);
            return new OkResponse();
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            Angajat user=logReq.getUser();

            server.logout(user, this);
            connected=false;
            return new OkResponse();
        }

        if (request instanceof GetAllAngajatsRequest){
            System.out.println("GetAllAngajatiRequest request");
            return new GetAllAngajatsResponse(server.getAllAngajati());
        }

        if (request instanceof GetAllProbaExtinsRequest){
            System.out.println("GetAllProbaExtinsRequest request");
            return new GetAllProbaExtinsResponse(server.getAllProbaExtins());
        }

        if (request instanceof GetAllParticipantsByIdRequest){
            System.out.println("GetAllParticipantsByIdRequest request");
            return new GetAllParticipantsByIdResponse(server.getAllParticipants(((GetAllParticipantsByIdRequest) request).getId()));
        }

        if(request instanceof GetMaxIdRequest){
            System.out.println("GetMaxIdRequest request");
            return  new GetMaxIdResponse(server.getMaxId());
        }

        if (request instanceof AddParticipantRequest) {
            System.out.println("AddParticipantRequest request");
            AddParticipantRequest participantRequest = (AddParticipantRequest) request;
            Participant participant = participantRequest.getParticipant();

            server.addParticipant(participant);
            return new OkResponse();
        }

        if (request instanceof AddInscriereRequest) {
            System.out.println("AddInscriereRequest request");
            AddInscriereRequest inscriereRequest = (AddInscriereRequest) request;
            Inscriere inscriere = inscriereRequest.getInscriere();

            server.addInscriere(inscriere);
            return new OkResponse();
        }

        return response;
    }

    @Override
    public void update(Iterable<ProbaExtins> probe) {
        sendResponse(new NotifyResponse(probe));
    }
}

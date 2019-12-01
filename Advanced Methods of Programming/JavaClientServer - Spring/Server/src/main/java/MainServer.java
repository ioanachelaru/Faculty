import Repo.RepoAngajat;
import Repo.RepoInscriere;
import Repo.RepoParticipant;
import Repo.RepoProba;
import Server.ServerImpl;
import Services.IServer;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.*;
import java.util.Properties;

public class MainServer {
    public static void main(String[] args) throws IOException, ServerException {

        int port = 55555;

        Properties serverProps=new Properties();
        serverProps.load(new FileReader("Server/src/bd.properties"));

        RepoAngajat repoAngajat = new RepoAngajat(serverProps);
        RepoParticipant repoParticipant = new RepoParticipant(serverProps);
        RepoProba repoProba = new RepoProba(serverProps);
        RepoInscriere repoInscriere = new RepoInscriere(serverProps);

        IServer serverImpl=new ServerImpl(repoAngajat,repoParticipant,repoProba,repoInscriere);

        AbstractServer server = new ObjectConcurrentServer(port, serverImpl);
        server.start();
    }
}
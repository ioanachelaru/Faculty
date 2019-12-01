package Server;


import Model.*;
import Repo.RepoAngajat;
import Repo.RepoInscriere;
import Repo.RepoParticipant;
import Repo.RepoProba;
import Services.IObserver;
import Services.IServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements IServer {

    private RepoAngajat repoAngajat;
    private RepoParticipant repoParticipant;
    private RepoProba repoProba;
    private RepoInscriere repoInscriere;
    private Map<String, IObserver> loggedClients;

    public ServerImpl(RepoAngajat repoAngajat, RepoParticipant repoParticipant,
                      RepoProba repoProba, RepoInscriere repoInscriere) {
        this.repoAngajat = repoAngajat;
        this.repoParticipant = repoParticipant;
        this.repoProba = repoProba;
        this.repoInscriere = repoInscriere;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Angajat user, IObserver client) {
        Angajat angajat = repoAngajat.findOne(user.getId());
        if (angajat != null){
            loggedClients.put(user.getId(),client);
            System.out.println("Login successufully!!\n");
        }
    }

    @Override
    public synchronized void logout(Angajat user, IObserver client) {
        loggedClients.remove(user.getId());
    }

    @Override
    public synchronized List<Angajat> getAllAngajati() {
        return (List<Angajat>) repoAngajat.findAll();
    }

    @Override
    public List<ProbaExtins> getAllProbaExtins() {
        List<ProbaExtins> lista = new ArrayList<>();
        Iterable<Proba> probe = repoProba.findAll();
        for (Proba p:probe) {
            ProbaExtins pe = new ProbaExtins(p.getId(), p.distantaInt(),p.getStil().toString()
                    ,repoProba.numarInscrieri(p.getId()));
            lista.add(pe);
        }
        return lista;
    }

    @Override
    public Iterable<Participant> getAllParticipants(String idProba) {
        List<Participant> participants = new ArrayList<>();
        for (Participant p:this.repoParticipant.findAll()) {
            String temp = idProba + String.valueOf(p.getId());
            if(this.repoInscriere.findOne(Integer.valueOf(temp)) != null)
                participants.add(p);
        }
        return participants;
    }

    @Override
    public Integer getMaxId() {
        return this.repoParticipant.size() + 1;
    }

    @Override
    public void addParticipant(Participant participant) {
        this.repoParticipant.save(participant);
    }

    @Override
    public void addInscriere(Inscriere inscriere) {
        this.repoInscriere.save(inscriere);
        notifyUpdate();

    }

    private final int nrThreads=7;
    private synchronized void notifyUpdate() {
        ExecutorService executor= Executors.newFixedThreadPool(nrThreads);
        for(IObserver Client :loggedClients.values()){
            if (Client!=null)
                executor.execute(() -> {
                    System.out.println("Notifying [" + Client.toString());
                    try {
                        Client.update(getAllProbaExtins());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
        executor.shutdown();
    }
}

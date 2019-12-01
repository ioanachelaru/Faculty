package service;

import domain.Angajat;
import repo.RepoAngajat;
import repo.RepoException;

public class ServiceAngajat {
    private RepoAngajat repoAngajat;

    public ServiceAngajat(RepoAngajat ra){
        this.repoAngajat=ra;
    }

    public void save(Angajat object) {
        this.repoAngajat.save(object);
    }

    public Iterable<Angajat> findAll() {
        return this.repoAngajat.findAll();
    }

    public Angajat findOne(String s) {
        Angajat angajat = this.repoAngajat.findOne(s);
        if(angajat==null)
            throw new RepoException("Angajat inexistent!");
        else return angajat;
    }

    public int size(){ return this.repoAngajat.size(); }
}

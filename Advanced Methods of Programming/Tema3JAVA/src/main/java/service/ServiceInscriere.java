package service;

import domain.Inscriere;
import repo.RepoException;
import repo.RepoInscriere;

public class ServiceInscriere {
    private RepoInscriere repoInscriere;

    public ServiceInscriere(RepoInscriere repoInscriere){ this.repoInscriere=repoInscriere; }

    public void save(Inscriere inscriere){
        Inscriere i=this.repoInscriere.findOne(inscriere.getId());
        if(i==null) this.repoInscriere.save(inscriere);
        else throw new RepoException("Participantul este deja inscris la acesta proba!");
    }

    public Iterable<Inscriere> findAll() { return this.repoInscriere.findAll(); }

    public Inscriere findOne(Integer id){ return this.repoInscriere.findOne(id); }

    public Integer size(){ return this.repoInscriere.size(); }
}

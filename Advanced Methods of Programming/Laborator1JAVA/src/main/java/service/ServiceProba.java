package service;

import domain.Proba;
import repo.RepoException;
import repo.RepoProba;

public class ServiceProba {
    private RepoProba repoProba;

    public ServiceProba(RepoProba repoProba){
        this.repoProba=repoProba;
    }

    public void save(Proba object){
        Proba proba=this.repoProba.findOne(object.getId());
        if(proba==null)
            this.repoProba.save(object);
        else throw new RepoException("Proba exista deja!");
    }

    public int size(){ return this.repoProba.size(); }

    public Proba findOne(Integer integer){
        Proba proba=this.repoProba.findOne(integer);
        if(proba==null)
            throw new RepoException("Proba nu exista!");
        else return proba;
    }

    public Iterable<Proba> findAll(){ return this.repoProba.findAll(); }

    public Integer numarInscrieri(Integer id){ return this.repoProba.numarInscrieri(id); }

}

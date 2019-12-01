package services;

import domain.Abonat;
import repos.RepoAbonat;

public class ServiceAbonat implements CRUDService<String, Abonat> {
    private RepoAbonat repoAbonat;

    public ServiceAbonat(RepoAbonat repoAbonat){
        this.repoAbonat = repoAbonat;
    }

    @Override
    public void save(Abonat entity) {
        this.repoAbonat.save(entity);
    }

    @Override
    public void delete(String s) {
        this.repoAbonat.delete(s);
    }

    @Override
    public void update(Abonat entity) {
        this.repoAbonat.update(entity);
    }

    @Override
    public Abonat findOne(String s) {
        return this.repoAbonat.findOne(s);
    }

    @Override
    public Iterable<Abonat> findAll() {
        return this.repoAbonat.findAll();
    }
}

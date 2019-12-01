package services;

import domain.Bibliotecar;
import repos.RepoBibliotecar;

public class ServiceBibliotecar implements CRUDService<String, Bibliotecar> {
    private RepoBibliotecar repoBibliotecar;

    public ServiceBibliotecar(RepoBibliotecar repoBibliotecar){
        this.repoBibliotecar = repoBibliotecar;
    }

    @Override
    public void save(Bibliotecar entity) {
        this.repoBibliotecar.save(entity);
    }

    @Override
    public void delete(String s) {
        this.repoBibliotecar.delete(s);
    }

    @Override
    public void update(Bibliotecar entity) {
        this.repoBibliotecar.update(entity);
    }

    @Override
    public Bibliotecar findOne(String s) {
        return this.repoBibliotecar.findOne(s);
    }

    @Override
    public Iterable<Bibliotecar> findAll() {
        return this.repoBibliotecar.findAll();
    }
}

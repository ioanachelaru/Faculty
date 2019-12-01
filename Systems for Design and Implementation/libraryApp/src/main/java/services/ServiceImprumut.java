package services;

import domain.Exemplar;
import domain.Imprumut;
import repos.RepoImprumut;
import utils.Observable;
import utils.Event;
import utils.EventType;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceImprumut implements CRUDService<Integer, Imprumut>, Observable<Event> {
    private RepoImprumut repoImprumut;
    List<Observer<Event>> observers = new ArrayList<>();

    public ServiceImprumut(RepoImprumut repoImprumut){
        this.repoImprumut = repoImprumut;
    }

    public int getId(){ return this.repoImprumut.getId(); }

    @Override
    public void save(Imprumut entity) {
        this.repoImprumut.save(entity);
        this.notifyObservers(new Event(EventType.ADDIMPRUMUT,entity));
    }

    @Override
    public void delete(Integer integer) {
        this.repoImprumut.delete(integer);
    }

    @Override
    public void update(Imprumut entity) {
        this.repoImprumut.update(entity);
        this.notifyObservers(new Event(EventType.RETURNBOOK,entity));
    }

    @Override
    public Imprumut findOne(Integer integer) {
        return this.repoImprumut.findOne(integer);
    }

    @Override
    public Iterable<Imprumut> findAll() {
        return this.repoImprumut.findAll();
    }

    @Override
    public void addObserver(Observer<Event> e) { this.observers.add(e); }

    @Override
    public void notifyObservers(Event s) {
        this.observers.stream().forEach(eventObserver -> eventObserver.update(s));
    }
}

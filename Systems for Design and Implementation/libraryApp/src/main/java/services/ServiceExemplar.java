package services;

import domain.Exemplar;
import repos.RepoExemplar;
import utils.Event;
import utils.EventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceExemplar implements CRUDService<Integer, Exemplar>, Observable<Event> {
    private RepoExemplar repoExemplar;
    List<Observer<Event>> observers = new ArrayList<>();

    public ServiceExemplar(RepoExemplar repoExemplar) { this.repoExemplar = repoExemplar; }

    public int getId() { return this.repoExemplar.getId(); }

    @Override
    public void save(Exemplar entity) {
        this.repoExemplar.save(entity);
        this.notifyObservers(new Event(EventType.ADDEXEMPLAR,entity));
    }

    @Override
    public void delete(Integer integer) { this.repoExemplar.delete(integer); }

    public void deleteByBook(Integer id) { this.repoExemplar.deleteByBook(id); }

    @Override
    public void update(Exemplar entity) {
        this.repoExemplar.update(entity);
        this.notifyObservers(new Event(EventType.ADDIMPRUMUT,entity));
    }

    @Override
    public Exemplar findOne(Integer integer) { return this.repoExemplar.findOne(integer); }

    @Override
    public Iterable<Exemplar> findAll() { return this.repoExemplar.findAll(); }

    @Override
    public void addObserver(Observer<Event> e) { this.observers.add(e); }

    @Override
    public void notifyObservers(Event s) {
        this.observers.stream().forEach(eventObserver -> eventObserver.update(s));
    }
}

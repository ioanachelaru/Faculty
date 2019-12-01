package services;

import domain.Carte;
import repos.RepoCarte;
import utils.Event;
import utils.EventType;
import utils.Observable;
import utils.Observer;
import java.util.ArrayList;
import java.util.List;

public class ServiceCarte implements CRUDService<Integer, Carte>, Observable<Event> {
    private RepoCarte repoCarte;
    List<Observer<Event>> observers = new ArrayList<>();

    public ServiceCarte(RepoCarte repoCarte){
        this.repoCarte = repoCarte;
    }

    public int getId(){ return this.repoCarte.getId(); }

    @Override
    public void save(Carte entity) {
        this.repoCarte.save(entity);
        this.notifyObservers(new Event(EventType.ADDCARTE,entity));
    }

    @Override
    public void delete(Integer integer) {
        this.repoCarte.delete(integer);
        this.notifyObservers(new Event(EventType.DELETECARTE,integer));
    }

    @Override
    public void update(Carte entity) {
        this.repoCarte.update(entity);
        this.notifyObservers(new Event(EventType.UPDATECARTE,entity));
    }

    @Override
    public Carte findOne(Integer integer) {
        return this.repoCarte.findOne(integer);
    }

    @Override
    public Iterable<Carte> findAll() {
        return this.repoCarte.findAll();
    }

    public Iterable<Carte> findAllBooksAvailable(){
        ArrayList<Carte> carteIterable = new ArrayList<>();

        for (Carte c : this.repoCarte.findAll()) {
            if(c.getNrExemplare() >= 1)
                carteIterable.add(c);
        }
        return carteIterable;
    }

    @Override
    public void addObserver(Observer<Event> e) { observers.add(e); }

    @Override
    public void notifyObservers(Event s) {
        this.observers.stream().forEach(eventObserver -> eventObserver.update(s));
    }
}

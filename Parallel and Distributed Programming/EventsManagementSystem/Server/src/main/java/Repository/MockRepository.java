package Repository;

import Domain.Event;
import Domain.Sale;
import Domain.Seat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MockRepository {
    private List<Event> events;
    private List<Sale> sales;

    private Double currentSold;
    private Double lastCheckSold;
    private Double balance;

    private ReentrantReadWriteLock lock;

    public MockRepository() {
        this.events = new ArrayList<>();
        this.sales = new ArrayList<>();

        this.currentSold = 0.0;
        this.lastCheckSold = 0.0;
        this.balance = 0.0;

        this.lock = new ReentrantReadWriteLock();

        this.populateEvents();
    }

    private void populateEvents() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("A","1A",50.0, true));
        seats.add(new Seat("A","2A",50.0, true));
        seats.add(new Seat("A","3A",50.0, true));
        seats.add(new Seat("A","4A",50.0, true));
        seats.add(new Seat("B","1B",35.0, true));
        seats.add(new Seat("B","2B",35.0, true));
        seats.add(new Seat("B","3B",35.0, true));
        seats.add(new Seat("B","4B",35.0, true));
        seats.add(new Seat("C","1C",15.0, true));
        seats.add(new Seat("C","2C",15.0, true));
        seats.add(new Seat("C","3C",15.0, true));
        seats.add(new Seat("C","4C",15.0, true));

        this.events.add(new Event(1, LocalDate.now().toString(),"Fuego in concert","minunta", seats));
        this.events.add(new Event(2, LocalDate.now().toString(),"Hrusca in concert","fabulos", seats));
        this.events.add(new Event(3, LocalDate.now().toString(),"Stefan Banica Jr. in concert","uau", seats));
        this.events.add(new Event(4, LocalDate.now().toString(),"Ducu Berti in concert","spectaculos", seats));
        this.events.add(new Event(5, LocalDate.now().toString(),"Emeric Imre in concert","mirobolant", seats));

    }

    public List<Event> getEvents() { return events; }

    public void setEvents(List<Event> events) { this.events = events; }

    public List<Sale> getSales() { return sales; }

    public void setSales(List<Sale> sales) { this.sales = sales; }

    public Double getCurrentSold() { return currentSold; }

    public void setCurrentSold(Double balance) { this.currentSold = balance; }

    private int generateIdSale(){ return this.getSales().size() + 1; }

    public Double getLastCheckSold() { return lastCheckSold; }

    public void setLastCheckSold(Double lastCheckSold) { this.lastCheckSold = lastCheckSold; }

    public Double getBalance(){ return this.balance; }

    public void setBalance( Double balance ){ this.balance = balance; }

    public void addSale(int id_event, String date, List<Seat> seats) {
        this.lock.readLock().lock();
        int id_sale = this.generateIdSale();

        this.sales.add(new Sale(id_sale, id_event, date, seats));

        for (Seat s:seats) {
            this.currentSold += s.getPrice();
        }
        this.lock.readLock().unlock();
    }

    public void setSeat(int id_event, Seat seat) {
        this.lock.readLock().lock();
        for (Event event: this.events) {
            if(event.getId() == id_event){
                for (Seat s: event.getSeats()) {
                    if(s.getName().equals(seat.getName())){
                        s.setAvailability(false);
                        break;
                    }
                }
                break;
            }
        }
        this.lock.readLock().unlock();
    }
}

package Services;

import Domain.Event;
import Domain.Sale;
import Domain.Seat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Service {
    private ReentrantReadWriteLock lock;

    private HashMap<Integer, Event> events;
    private ConcurrentLinkedQueue<Sale> sales;

    private Double lastCheckSold;
    private Double currentSold;

    public Service(){
        this.lock = new ReentrantReadWriteLock();
        this.events = new HashMap<>();
        this.sales = new ConcurrentLinkedQueue<>();
        lastCheckSold = 0.0;
        currentSold = 0.0;

        this.LoadEvents();
    }

    private void LoadEvents() {
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

        this.events.put(1,new Event(1, LocalDate.now().toString(),"Fuego in concert","minunta", seats));
        this.events.put(2,new Event(2, LocalDate.now().toString(),"Hrusca in concert","fabulos", seats));
        this.events.put(3,new Event(3, LocalDate.now().toString(),"Stefan Banica Jr. in concert","uau", seats));
        this.events.put(4,new Event(4, LocalDate.now().toString(),"Ducu Berti in concert","spectaculos", seats));
        this.events.put(5,new Event(5, LocalDate.now().toString(),"Emeric Imre in concert","mirobolant", seats));
    }

    public void addSale(String clientName, Integer productId, Integer quantity) {
        this.lock.readLock().lock();

        // Event not found
        if (!stocks.containsKey(productId)){
            lock.readLock().unlock();
            throw new RuntimeException("Product: " + productId + "doesn't exist!");
        }
        // lock stock for the product with the given product id
        synchronized (stocks.get(productId)) {
            Stock s = stocks.get(productId);
            if (s.getQuantity() < quantity){
                lock.readLock().unlock();
                throw new RuntimeException("Quantity too small: " + productId);
            }
            // Update quantity
            s.setQuantity(s.getQuantity() - quantity);

            // Register new bill
            Product p = stocks.get(productId).getProduct();
            Bill bill = new Bill(p.getId(), quantity, quantity * p.getPrice(), clientName);
            bills.add(bill);

            // Update current sold
            currentSold += (quantity * p.getPrice());
        }

        lock.readLock().unlock();
    }
}

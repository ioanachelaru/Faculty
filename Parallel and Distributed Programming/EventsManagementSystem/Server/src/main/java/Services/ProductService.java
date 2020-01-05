package Services;

import Domain.Event;
import Domain.Sale;
import Domain.Seat;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProductService {
    private ReentrantReadWriteLock lock;
    private HashMap<Integer, Event> events;
    private ConcurrentLinkedQueue<Sale> sales;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Double lastCheckSold;
    private Double currentSold;

    public ProductService(){
        lock = new ReentrantReadWriteLock();
        events = new HashMap<>();
        sales = new ConcurrentLinkedQueue<>();
        lastCheckSold = 0.0;
        currentSold = 0.0;

        LoadEvents();
    }

    public void Buy(int id_event,String clientName, String date, List<Seat> seats) {
        lock.readLock().lock();

        // Product not found
        if (!events.containsKey(id_event)){
            lock.readLock().unlock();
            throw new RuntimeException("Event: " + id_event + "doesn't exist!");
        }
        // lock event with the given id
        synchronized (events.get(id_event)) {

            // Update seats of event
            seats.forEach(seat -> this.setSeat(id_event,seat));

            // Register new sale
            Sale sale = new Sale(this.generateIdSale(), clientName,id_event,date,seats);
            sales.add(sale);
        }

        lock.readLock().unlock();
    }

    private int generateIdSale(){ return this.sales.size() + 1; }

    public void setSeat(int id_event, Seat seat) {
        for (Seat s:this.events.get(id_event).getSeats()) {
            if(s.getName().equals(seat.getName())){
                this.currentSold += s.getPrice();
                s.setAvailability(false);
                break;
            }
        }
    }

    public void Check(){
        lock.writeLock().lock();

        try(FileWriter fw = new FileWriter("D:\\Faculty\\Parallel and Distributed Programming\\EventsManagementSystem\\src\\main\\resources\\log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.write("[ STARTED CHECKING -  " + dateFormat.format(new Date()) + " ]\n\n");

            Double totalBillsValue = CalculateTotalSales();

            out.write("[INFO] Last Sold - " + lastCheckSold + "\n");
            out.write("[INFO] Current Sold - " + currentSold + "\n");
            out.write("[INFO] Sales Total - " + totalBillsValue + "\n");

            Double rt = totalBillsValue + lastCheckSold;

            if( Math.abs(rt - currentSold)  > 0.00001) {
                out.write("[ERROR] Corrupted sold!");
            }
            else {
                out.write("[INFO] Sales:\n\n");
                // Dump sales
                for (Sale b : sales){
                    out.write(b.toString() + "\n");
                }
            }
            out.write("\n[ FINISHED CHECKING -  " + dateFormat.format(new Date()) + " ]\n\n");

            // Free sales
            lastCheckSold = currentSold;
            sales.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

        lock.writeLock().unlock();
    }

    public List<Event> GetEvents(){
        lock.readLock().lock();

        ArrayList<Event> availableEvents = new ArrayList<>();
        for(Integer id : events.keySet()){
            synchronized (events.get(id)) {
                availableEvents.add(events.get(id));
            }
        }

        lock.readLock().unlock();
        return availableEvents;
    }

    private Double CalculateTotalSales(){
        Double total = 0.0;
        for(Sale s : sales) {
            total += s.getPrice();
        }

        return total;
    }

    private void LoadEvents(){

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

        try(BufferedReader br = new BufferedReader(new FileReader("D:\\Faculty\\Parallel and Distributed Programming\\EventsManagementSystem\\src\\main\\resources\\products.txt"))) {
            String line;
            while (null != (line = br.readLine())) {
                String[] parts = line.split(",");

                Event event = new Event(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],seats);
                events.put(event.getId(), event);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Seat> GetSeatsForEvent(int idEvent) {
        this.lock.readLock().lock();
        List<Seat> avlSeats = new ArrayList<>();
        for(Seat seat : this.events.get(idEvent).getSeats()){
            if(seat.getAvailability()){
                avlSeats.add(seat);
            }
        }
        this.lock.readLock().unlock();
        return avlSeats;
    }
}

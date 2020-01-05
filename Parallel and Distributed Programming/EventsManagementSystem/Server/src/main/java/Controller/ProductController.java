package Controller;

import Domain.Event;
import Domain.Seat;
import Services.ProductService;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductController {
    private ProductService productService;
    private ExecutorService executorService;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void Stop(){
        executorService.shutdownNow();
    }

    public Future<Void> Check(){
        Callable<Void> worker = new CheckCallback();
        return executorService.submit(worker);
    }

    public Future<Boolean> Buy(int id_event,String clientName, String date, List<Seat> seats){
        Callable<Boolean> worker = new BuyCallback(id_event, clientName, date,seats);
        return executorService.submit(worker);
    }

    public Future<List<Event>> GetEvents(){
        Callable<List<Event>> worker = new GetEventCallback();
        return executorService.submit(worker);
    }

    public Future<List<Seat>> GetSeatsForEvent(int id_event) {
        Callable<List<Seat>> worker = new GetSeatsForEventCallback(id_event);
        return executorService.submit(worker);
    }

    private class CheckCallback implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            productService.Check();
            return null;
        }
    }

    private class BuyCallback implements Callable<Boolean> {
        private final int id_event;
        private String clientName;
        private final String date;
        private final List<Seat> seats;

        public BuyCallback(int id_event,String clientName, String date, List<Seat> seats) {
            this.id_event = id_event;
            this.clientName = clientName;
            this.date = date;
            this.seats = seats;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                productService.Buy(id_event, clientName, date,seats);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private class GetEventCallback implements Callable<List<Event>>{
        @Override
        public List<Event> call() throws Exception {
            return productService.GetEvents();
        }
    }

    private class GetSeatsForEventCallback implements Callable<List<Seat>> {
        private int idEvent;

        public GetSeatsForEventCallback(int id_event) {
            this.idEvent = id_event;
        }

        @Override
        public List<Seat> call() throws Exception {
            return productService.GetSeatsForEvent(idEvent);
        }
    }
}

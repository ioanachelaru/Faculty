package Repository;

import Domain.Event;
import Domain.Sale;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {
    private List<Event> events;
    private List<Sale> sales;
    private Double balance;

    public MockRepository() {
        this.events = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.balance = 0.0;
    }

    public List<Event> getEvents() { return events; }

    public void setEvents(List<Event> events) { this.events = events; }

    public List<Sale> getSales() { return sales; }

    public void setSales(List<Sale> sales) { this.sales = sales; }

    public Double getBalance() { return balance; }

    public void setBalance(Double balance) { this.balance = balance; }
}

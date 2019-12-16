package Server;

import Domain.Event;
import Domain.Sale;
import java.util.List;

public interface IServer {
    void addSale(String date, List<String> seats);
    List<Event> printAllEventsAvailable();
}

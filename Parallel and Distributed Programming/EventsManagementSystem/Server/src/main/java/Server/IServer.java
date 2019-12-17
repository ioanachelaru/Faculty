package Server;

import Domain.Event;
import Domain.Seat;

import java.util.List;

public interface IServer {
    void addSale(int id_event, String date, List<Seat> seats);
    List<Event> getAllEventsAvailable();
    void markSeat(int id_event, Seat seat);
}

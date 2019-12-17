package Server;

import Domain.Event;
import Domain.Seat;
import Repository.MockRepository;
import java.util.ArrayList;
import java.util.List;

public class ServerImplementation implements IServer {
    private final MockRepository mockRepository;

    public ServerImplementation(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    @Override
    public void addSale(int id_event, String date, List<Seat> seats) {
        this.mockRepository.addSale(id_event, date, seats);
    }

    @Override
    public List<Event> getAllEventsAvailable() {
        List<Event> list = new ArrayList<>();
        for (Event event : this.mockRepository.getEvents()) {
            for (Seat seat : event.getSeats()) {
                if(seat.getAvailability()){
                    list.add(event);
                    break;
                }
            }
        }
        return list;
    }

    @Override
    public void markSeat(int id_event, Seat seat) {
        this.mockRepository.setSeat(id_event, seat);
    }
}

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
    public void addSale(String date, List<String> seats) {
        this.mockRepository.addSale(date, seats);
    }

    @Override
    public List<Event> printAllEventsAvailable() {
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
}

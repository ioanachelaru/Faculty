import Domain.Event;
import Domain.Seat;
import Server.IServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends UnicastRemoteObject {

    private IServer server;

    public Client() throws RemoteException { }

    public void setServer(IServer server) { this.server = server; }

    private void showMenu(){
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("1 -> Print all events available");
        System.out.println("2 -> Buy tickets to an event");
        System.out.println("3 -> Exit");
        System.out.println("---------------------------------");
        System.out.println();
    }

    private void handlePrintEvents(){
        List<Event> events_available = this.server.getAllEventsAvailable();
        for (Event event: events_available) {
            System.out.println(event.getId() + ": " + event.getDate() + " " +
                    event.getTitle() + " " + event.getDescription());
        }
    }

    private void hadlePrintSeatsAvailable(int id_event){
        List<Event> events_available = this.server.getAllEventsAvailable();
        List<Seat> seats = new ArrayList<>();
        for (Event event: events_available) {
            if(event.getId() == id_event){
                for (Seat s: event.getSeats()) {
                    if(s.getAvailability())
                        seats.add(s);
                }
                break;
            }
        }

        for (Seat s: seats) {
            System.out.println(s.getType() + " " + s.getName() + " " + s.getPrice());
        }
    }

    private Seat getSeatOfGivenEvent(int id_event, String seat_name){
        List<Event> events = this.server.getAllEventsAvailable();

        Seat seat_found = new Seat("nonexistent","1A",0.0, false);

        for (Event event: events) {
            if(event.getId() == id_event){
                for (Seat seat: event.getSeats()) {
                    if(seat.getName().equals(seat_name)) {
                        this.server.markSeat(id_event, seat);
                        seat_found = seat;
                        break;
                    }
                }
                break;
            }
        }

        return seat_found;
    }

    public void run(){

        Scanner scanner = new Scanner(System.in);
        String s;

        while (true){
            this.showMenu();

            s = scanner.next();

            if(s.equals("1"))
                this.handlePrintEvents();

            else if(s.equals("2")){
                this.handlePrintEvents();

                System.out.println("Pick an event");

                int id_picked_event = Integer.parseInt(scanner.next());
                this.hadlePrintSeatsAvailable(id_picked_event);

                System.out.println("How many seats do you want to book?");
                int nr_seats = Integer.parseInt(scanner.next());

                System.out.println("Pick your seats");

                List<Seat> seats_in = new ArrayList<>();
                while (nr_seats > 0){
                    String seat_name = scanner.next();

                    Seat seat = this.getSeatOfGivenEvent(id_picked_event, seat_name);
                    if(seat.getType().equals("nonexistent"))
                        System.out.println("It seems like you typed a non-existent seat. Try harder next time");
                    else{
                        seats_in.add(seat);
                        nr_seats--;
                    }
                }
                String date = LocalDate.now().toString();
                this.server.addSale(id_picked_event, date, seats_in);

            }
            else if(s.equals("3"))
                break;
            else
                System.out.println("Not a valid command, try harder");
        }
    }

}

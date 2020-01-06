import Domain.Event;
import Domain.Seat;
import Requests.BuyRequest;
import Requests.GetAllEventsAvailableRequest;
import Requests.GetAllSeatsForEventRequest;
import Responses.BuyResponse;
import Responses.GetAllEventsAvailableResponse;
import Responses.GetAllSeatsAvailableResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static List<Seat> avlSeats;
    public static void showMenu(){
        System.out.println("1.Print available products");
        System.out.println("2.Buy product");
        System.out.println("3.Exit");
    }

    public static void handlePrintResponse(GetAllEventsAvailableResponse response){
        for(Event s : response.getResponse()){
            System.out.println("Event{" +
                    "id=" + s.getId() +
                    ", date='" + s.getDate() + '\'' +
                    ", title='" + s.getTitle() + '\'' +
                    ", description='" + s.getDescription() + '\''+
                    '}');
        }
    }

    public static void handlePrintSeatsResponse(GetAllSeatsAvailableResponse response){
        avlSeats = new ArrayList<>();
        for(Seat s : response.getResponse()){
            System.out.println(s);
            avlSeats.add(s);
        }
    }

    public static void handleBuyResponse(BuyResponse response){
        if(response.getOkResponse()){
            System.out.println("Success!");
        }
        else{
            System.out.println("Internal error. Please retry");
        }
    }

    public static BuyRequest Buy(int id){
        Scanner in = new Scanner(System.in);

        System.out.println("clientName: ");
        String name = in.next();



        System.out.println("How many seats do you want to book?");
        int nr_seats = Integer.parseInt(in.next());

        System.out.println("Pick your seats");

        List<Seat> seats_in = new ArrayList<>();
        while (nr_seats > 0){
            String seat_name = in.next();

            Seat seat = new Seat("nonexistent","",0.0,false);
            for(Seat s : avlSeats){
                if(s.getName().equals(seat_name)){
                    seat = s;
                }
            }
            if(seat.getType().equals("nonexistent"))
                System.out.println("It seems like you typed a non-existent seat. Try harder next time");
            else{
                seats_in.add(seat);
                nr_seats--;
            }
        }

        return new BuyRequest(name, id, LocalDate.now().toString(),seats_in);
    }

    public static GetAllSeatsForEventRequest getSeats(int id){
        return new GetAllSeatsForEventRequest(id);
    }

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("127.0.0.1", 4444);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);

        String s = "";
        while(!s.equals("3")){
            showMenu();
            s = scanner.next();

            if(s.equals("1")){
                // send get event request
                outputStream.writeObject(new GetAllEventsAvailableRequest());
                // handle event response
                handlePrintResponse((GetAllEventsAvailableResponse)inputStream.readObject());

            }
            else if (s.equals("2")){
                Scanner in = new Scanner(System.in);
                System.out.println("eventId: ");
                Integer id = in.nextInt();


                outputStream.writeObject(getSeats(id));

                handlePrintSeatsResponse((GetAllSeatsAvailableResponse) inputStream.readObject());

                // send buy request
                outputStream.writeObject(Buy(id));
                // handle buy response
                handleBuyResponse((BuyResponse)inputStream.readObject());
            }
            else if (!s.equals("3")){
                System.out.println("Not a valid option!! Please type a valid one this time :) ");
            }

            outputStream.reset();
        }

        inputStream.close();
        outputStream.close();
        socket.close();

    }
}

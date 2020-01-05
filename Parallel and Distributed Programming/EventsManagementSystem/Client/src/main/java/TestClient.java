import Domain.Event;
import Domain.Seat;
import Requests.BuyRequest;
import Requests.GetAllEventsAvailableRequest;
import Responses.GetAllEventsAvailableResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestClient {

    private static List<Event> events;
    private static Random random = new Random();

    private static BuyRequest CreateBuyRequest(){
        int id = random.nextInt(events.size());
        Seat seat = events.get(id).getSeats().get(0);
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        return new BuyRequest("TestClient",id, LocalDate.now().toString(),seats);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", 4444);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        outputStream.writeObject(new GetAllEventsAvailableRequest());

        events = ((GetAllEventsAvailableResponse)inputStream.readObject()).getResponse();
        // create buy requests until server throws exception
        while(true){
            try {
                outputStream.writeObject(CreateBuyRequest());
                inputStream.readObject();   // check result of buy request
                Thread.sleep(1500);
            }catch (Exception ignored){
                break;
            }
        }
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}

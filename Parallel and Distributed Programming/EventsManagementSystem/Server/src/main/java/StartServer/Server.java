package StartServer;

import Controller.ProductController;
import Domain.Event;
import Domain.Seat;
import Requests.BuyRequest;
import Requests.GetAllEventsAvailableRequest;
import Requests.GetAllSeatsForEventRequest;
import Responses.BuyResponse;
import Responses.GetAllEventsAvailableResponse;
import Responses.GetAllSeatsAvailableResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private ProductController productController;

    public void start(int port, ProductController productController) throws IOException {
        serverSocket = new ServerSocket(port);
        this.productController = productController;
        executorService = Executors.newFixedThreadPool(10);

        while (true)
            executorService.submit(new EchoClientHandler(serverSocket.accept(), productController));
    }

    public void stop() throws IOException {
        executorService.shutdownNow();
        this.productController.Stop();
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        private ProductController productController;

        public EchoClientHandler(Socket socket, ProductController productController) {
            this.clientSocket = socket;
            this.productController = productController;
        }

        public void run() {
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(clientSocket.getInputStream());

                while(true){
                    Object request = inputStream.readObject();
                    if (request instanceof GetAllEventsAvailableRequest){
                        outputStream.writeObject(HandleGetEvents());
                    }
                    else if (request instanceof BuyRequest){
                        outputStream.writeObject(HandleBuyRequest((BuyRequest)request));
                    }
                    else if(request instanceof GetAllSeatsForEventRequest){
                        outputStream.writeObject(HandleGetSeatsForEvent((GetAllSeatsForEventRequest) request));
                    }else break;

                    outputStream.reset();
                }

                inputStream.close();
                outputStream.close();
                this.clientSocket.close();

            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception!!");
            }
        }

        public GetAllEventsAvailableResponse HandleGetEvents() throws ExecutionException, InterruptedException {
            Future<List<Event>> event = productController.GetEvents();
            return new GetAllEventsAvailableResponse(event.get());
        }

        public GetAllSeatsAvailableResponse HandleGetSeatsForEvent(GetAllSeatsForEventRequest request) throws ExecutionException, InterruptedException {
            Future<List<Seat>> s = productController.GetSeatsForEvent(request.getId_event());
            return new GetAllSeatsAvailableResponse(s.get());
        }

        public BuyResponse HandleBuyRequest(BuyRequest request) throws ExecutionException, InterruptedException {
            Future<Boolean> buy = productController.Buy(request.getId_event(), request.getClientName(), request.getDate(),request.getSeats());
            return new BuyResponse(buy.get());
        }

    }
}

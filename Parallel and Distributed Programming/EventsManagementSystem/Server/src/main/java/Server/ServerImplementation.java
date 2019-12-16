package Server;

import Domain.Sale;
import Repository.MockRepository;

public class ServerImplementation implements IServer {
    private final MockRepository mockRepository;

    public ServerImplementation(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    @Override
    public void addSale(Sale sale) {

    }
}

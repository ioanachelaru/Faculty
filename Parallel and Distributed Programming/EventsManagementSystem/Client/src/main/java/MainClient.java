import Server.IServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.rmi.RemoteException;

public class MainClient {
    public static void main(String[] args) throws RemoteException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServer server = (IServer) factory.getBean("managementSystemService");

        Client client = new Client();
        client.setServer(server);
        client.run();
    }
}

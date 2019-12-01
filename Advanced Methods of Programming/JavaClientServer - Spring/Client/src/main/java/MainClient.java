import Services.IServer;
import controllers.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objectprotocol.ServerObjectProxy;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        int defaultChatPort = 55555;
        String defaultServer = "localhost";

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        AnchorPane root = loader.load();

        IServer server = new ServerObjectProxy(defaultServer, defaultChatPort);

        ControllerLogin loginController = loader.getController();
        loginController.setServer(server);

        Scene scene = new Scene(root,700,500);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(100);
        stage.setTitle("AUTENTIFICARE");

        loginController.setPrimaryStage(stage);
        stage.show();
    }
}

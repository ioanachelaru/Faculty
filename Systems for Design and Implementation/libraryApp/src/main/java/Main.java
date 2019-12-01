import controllers.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repos.*;
import services.*;

public class Main extends Application {

    private RepoBibliotecar repoBibliotecar = new RepoBibliotecar();
    private RepoAbonat repoAbonat = new RepoAbonat();
    private RepoCarte repoCarte = new RepoCarte();
    private RepoExemplar repoExemplar = new RepoExemplar();
    private RepoImprumut repoImprumut = new RepoImprumut();

    private ServiceBibliotecar serviceBibliotecar = new ServiceBibliotecar(repoBibliotecar);
    private ServiceAbonat serviceAbonat = new ServiceAbonat(repoAbonat);
    private ServiceCarte serviceCarte = new ServiceCarte(repoCarte);
    private ServiceExemplar serviceExemplar = new ServiceExemplar(repoExemplar);
    private ServiceImprumut serviceImprumut = new ServiceImprumut(repoImprumut);

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        AnchorPane root = loader.load();

        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.setServices(serviceBibliotecar,serviceAbonat,serviceCarte,serviceExemplar,serviceImprumut);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        controllerLogin.setPrimaryStage(stage);


        stage.setTitle("Autentificare");
        stage.show();
    }
}

package main;

import controllers.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repo.*;
import service.ServiceAngajat;
import service.ServiceInscriere;
import service.ServiceParticipant;
import service.ServiceProba;
import validatori.ValidatorParticipant;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        Properties prop = new Properties();

        try {
            prop.load(new FileReader("src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RepoAngajat repoAngajat = new RepoAngajat(prop);
        RepoParticipant repoParticipant = new RepoParticipant(prop);
        RepoProba repoProba = new RepoProba(prop);
        RepoInscriere repoInscriere = new RepoInscriere(prop);

        ValidatorParticipant validatorParticipant=new ValidatorParticipant();

        ServiceAngajat serviceAngajat =new ServiceAngajat(repoAngajat);
        ServiceParticipant serviceParticipant = new ServiceParticipant(repoParticipant, validatorParticipant);
        ServiceProba serviceProba=new ServiceProba(repoProba);
        ServiceInscriere serviceInscriere=new ServiceInscriere(repoInscriere);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        AnchorPane root = loader.load();

        ControllerLogin controllerLogin=loader.getController();
        controllerLogin.setServices(serviceAngajat,serviceParticipant,serviceProba,serviceInscriere);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        controllerLogin.setPrimaryStage(stage);
        stage.setTitle("Autentificare");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
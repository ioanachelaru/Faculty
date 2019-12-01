package controllers;

import domain.Angajat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repo.RepoException;
import service.ServiceAngajat;
import service.ServiceInscriere;
import service.ServiceParticipant;
import service.ServiceProba;
import java.io.IOException;

public class ControllerLogin {
    private ServiceAngajat serviceAngajat;
    private ServiceParticipant serviceParticipant;
    private ServiceProba serviceProba;
    private ServiceInscriere serviceInscriere;

    private ControllerMainStage controllerMainStage;

    public TextField username;
    public TextField password;

    Stage primaryStage;
    Stage secondaryStage;

    public void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    public ControllerLogin(){}

    public void setServices(ServiceAngajat serviceAngajat,ServiceParticipant serviceParticipant,
                           ServiceProba serviceProba,ServiceInscriere serviceInscriere){
        this.serviceAngajat=serviceAngajat;
        this.serviceParticipant=serviceParticipant;
        this.serviceProba=serviceProba;
        this.serviceInscriere=serviceInscriere;
    }

    public void handleLogin() throws IOException {
        try{
            Angajat angajat = this.serviceAngajat.findOne(this.username.getText());

            if(angajat.getPasswordAngajat().equals(this.password.getText())){
                this.secondaryStage = new Stage();
                AnchorPane mainLayout = getMainLayout();

                Scene scene = new Scene(mainLayout);
                this.secondaryStage.setScene(scene);
                this.controllerMainStage.setPrimaryStage(this.secondaryStage);
                this.secondaryStage.setTitle("Main window");
                this.secondaryStage.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Parola incorecta :(");

                alert.showAndWait();
            }
        }catch (RepoException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Se pare ca nu aveti un cont valid :(");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    private AnchorPane getMainLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainStage.fxml"));
        AnchorPane mainLayout = loader.load();

        controllerMainStage=loader.getController();
        controllerMainStage.setServices(serviceParticipant,serviceProba,serviceInscriere);

        return mainLayout;
    }
}
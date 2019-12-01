package controllers;

import Model.Angajat;
import Services.IServer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerLogin {

    public TextField username;
    public TextField password;

    private Stage primaryStage;
    private Stage secondaryStage;

    private IServer server;
    private Angajat currentUser;

    public void setPrimaryStage(Stage primaryStage){ this.primaryStage = primaryStage; }

    public ControllerLogin(){}

    public void setServer(IServer s){
        server = s;
    }

    public void handleLogin() throws IOException {
        List<Angajat> angajati = this.server.getAllAngajati();
        String usernameString = this.username.getText();
        String passwordString = this.password.getText();

        List<Angajat> angajats1 = angajati.stream().filter(angajat -> angajat.getId().equals(usernameString)
                && angajat.getPasswordAngajat().equals(passwordString)).collect(Collectors.toList());
        if(angajats1.size() == 1){
            this.currentUser = angajats1.get(0);
            this.primaryStage.close();
            this.secondaryStage = new Stage();
            AnchorPane mainLayout = getMainLayout();
            Scene scene = new Scene(mainLayout);
            this.secondaryStage.setScene(scene);
            this.secondaryStage.show();
        }else {
            this.showError("Nume de utilizator sau parola sunt incorecte");
        }
    }

    private void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("EROARE");
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.show();
    }

    private AnchorPane getMainLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainStage.fxml"));
        AnchorPane mainLayout = loader.load();

        ControllerMainStage controllerMainStage = loader.getController();
        this.secondaryStage.setX(400);
        this.secondaryStage.setY(100);
        controllerMainStage.setPrimaryStage(this.secondaryStage);

        controllerMainStage.setServer(server);
        server.login(currentUser, controllerMainStage);

        return mainLayout;
    }
}
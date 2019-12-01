package controllers;

import domain.Abonat;
import domain.Bibliotecar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControllerLogin {
    public TextField username;
    public PasswordField password;

    private Stage primaryStage;
    private Stage secondaryStage;

    private ServiceBibliotecar serviceBibliotecar;
    private ServiceAbonat serviceAbonat;
    private ServiceCarte serviceCarte;
    private ServiceExemplar serviceExemplar;
    private ServiceImprumut serviceImprumut;

    public ControllerLogin(){}

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setServices(ServiceBibliotecar serviceBibliotecar, ServiceAbonat serviceAbonat,
                            ServiceCarte serviceCarte,ServiceExemplar serviceExemplar, ServiceImprumut serviceImprumut){
        this.serviceBibliotecar = serviceBibliotecar;
        this.serviceAbonat = serviceAbonat;
        this.serviceCarte = serviceCarte;
        this.serviceExemplar = serviceExemplar;
        this.serviceImprumut = serviceImprumut;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleLogin() throws IOException {
        Object user = this.serviceBibliotecar.findOne(this.username.getText());

        if(user != null){
            Bibliotecar bibliotecar = (Bibliotecar) user;

            if(bibliotecar.getPassword().equals(this.password.getText())){
                //this.primaryStage.close();
                this.secondaryStage = new Stage();
                AnchorPane bibliotecarLayout = getLayoutBibliotecar(bibliotecar);
                Scene scene = new Scene(bibliotecarLayout);
                this.secondaryStage.setScene(scene);
                this.secondaryStage.setResizable(false);
                this.secondaryStage.setTitle("User: bibliotecar");
                this.secondaryStage.show();
            }
            else this.showError("Parola incorecta!");
        }
        else{
            user = this.serviceAbonat.findOne(this.username.getText());

            if(user != null){
                Abonat abonat = (Abonat) user;

                if(abonat.getPassword().equals(this.password.getText())){
                    //this.primaryStage.close();
                    this.secondaryStage = new Stage();
                    AnchorPane abonatLayout = getLayoutAbonat(abonat);
                    Scene scene = new Scene(abonatLayout);
                    this.secondaryStage.setScene(scene);
                    this.secondaryStage.setResizable(false);
                    this.secondaryStage.setTitle("User: abonat");
                    this.secondaryStage.show();
                }
                else this.showError("Parola incorecta!");
            }
            else this.showError("Acest utilizator nu exista!");
        }
    }

    private AnchorPane getLayoutBibliotecar(Bibliotecar bibliotecar) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/librarianView.fxml"));
        AnchorPane panelLayout = loader.load();

        ControllerBibliotecar controllerBibliotecar = loader.getController();
        controllerBibliotecar.setServices(this.serviceAbonat, this.serviceCarte,this.serviceExemplar, this.serviceImprumut);
        controllerBibliotecar.setPrimaryStage(this.secondaryStage);

        return panelLayout;
    }

    private AnchorPane getLayoutAbonat(Abonat abonat) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/subscriberView.fxml"));
        AnchorPane panelLayout = loader.load();

        ControllerAbonat controllerAbonat = loader.getController();
        controllerAbonat.setAbonat(abonat);
        controllerAbonat.setServices(this.serviceCarte,this.serviceExemplar,this.serviceImprumut);
        controllerAbonat.setPrimaryStage(this.secondaryStage);

        return panelLayout;
    }

}

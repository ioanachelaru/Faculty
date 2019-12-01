package controllers;

import domain.Carte;
import domain.Exemplar;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCarte;
import services.ServiceExemplar;

public class ControllerAddExemplare {

    public TextField anTextField;
    public TextField nrExemplareTextField;

    private ServiceExemplar serviceExemplar;
    private ServiceCarte serviceCarte;

    private Stage primaryStage;

    private Carte carte;

    void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    public void setServices(ServiceCarte serviceCarte, ServiceExemplar serviceExemplar, Carte carte) {
        this.serviceCarte = serviceCarte;
        this.serviceExemplar = serviceExemplar;
        this.carte = carte;
    }

    private void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("");
        alert.setContentText(s);
        alert.show();
    }

    public void handleAdd(ActionEvent actionEvent) {
        Integer anulAparitiei = Integer.valueOf(this.anTextField.getText());
        Integer nrExemplare = Integer.valueOf(this.nrExemplareTextField.getText());
        boolean dateOk = true;

        // valideaza datele de intrare
        if(anulAparitiei < 1 || anulAparitiei > 2019) {
            this.showError("Anul aparitiei nu este in intervalul valid.");
            dateOk = false;
        }

        if(nrExemplare < 1) {
            this.showError("Trebuie sa adaugati minim un exemplar al acestei carti.");
            dateOk = false;
        }

        if(dateOk){
            int firstId = this.serviceExemplar.getId();
            Exemplar exemplar = new Exemplar();

            for(int i = 1; i <= nrExemplare; i++){
                exemplar = new Exemplar(firstId, this.carte.getId(), anulAparitiei);
                this.serviceExemplar.save(exemplar);
                firstId++;
            }

            this.carte.setNrExemplare(this.carte.getNrExemplare() + nrExemplare);

            this.serviceCarte.update(carte);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare");
            alert.setHeaderText("");
            alert.setContentText("Exemplare adaugate cu succes!");
            alert.show();
        }
    }

}

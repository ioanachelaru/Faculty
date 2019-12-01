package controllers;

import Model.Inscriere;
import Model.Participant;
import Model.Proba;
import Model.ProbaExtins;
import Services.IServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControllerInscriere extends UnicastRemoteObject {
    @FXML
    public TextField numeParticipant;
    @FXML
    public TextField varstaParticipant;

    @FXML
    public TableView<Proba> tableView;
    @FXML
    public TableColumn<Proba,Integer> idProbaColumn;
    @FXML
    public TableColumn<Proba,String> stilColumn;
    @FXML
    public TableColumn<Proba,Integer> distantaColumn;

    Stage primaryStage;
    IServer server;

    public ControllerInscriere() throws RemoteException {
        super();
    }

    public void setServer(IServer server) {
        this.server = server;
        this.populateTable();
    }

    private void populateTable() {
        this.idProbaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.stilColumn.setCellValueFactory(new PropertyValueFactory<>("stil"));
        this.distantaColumn.setCellValueFactory(new PropertyValueFactory<>("distantaInt"));

        this.tableView.setItems(this.getAll());
    }

    private ObservableList<Proba> getAll() {
        ObservableList<Proba> probe = FXCollections.observableArrayList();
        for(ProbaExtins pe:this.server.getAllProbaExtins()){
            Proba p = new Proba(pe.getId(),pe.distantaInt(),pe.getStil().toString());
            probe.add(p);
        }
        return probe;
    }

    public void setPrimaryStage(Stage thirdStage) { this.primaryStage=thirdStage; }

    public void handleInscrie() {
        Integer id = this.server.getMaxId();
        Participant participant = new Participant(id,this.numeParticipant.getText(),
                Integer.valueOf(this.varstaParticipant.getText()));

        if(this.tableView.getSelectionModel().getSelectedIndex()<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nicio proba selectata :(");
            alert.showAndWait();
        }
        else{
            Proba proba = this.tableView.getSelectionModel().getSelectedItem();
            this.server.addParticipant(participant);
            this.server.addInscriere(new Inscriere(proba.getId(), participant.getId()));
            }
    }

    public void handleBack() { this.primaryStage.close(); }
}
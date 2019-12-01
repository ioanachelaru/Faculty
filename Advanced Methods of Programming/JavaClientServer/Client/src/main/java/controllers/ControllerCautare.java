package controllers;

import Model.Participant;
import Services.IServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ControllerCautare extends UnicastRemoteObject {

    Integer id;
    IServer server;
    Stage primaryStage;

    @FXML
    private TableView<Participant> tableView;
    @FXML
    private TableColumn<Participant,Integer> idParticipantColumn;
    @FXML
    private TableColumn<Participant,String> numeParticipantColumn;
    @FXML
    private TableColumn<Participant,Integer> varstaParticipantColumn; 

    public ControllerCautare() throws RemoteException {
        super();
    }

    public void setPrimaryStage(Stage secondaryStage) { this.primaryStage = secondaryStage; }
    
    public void setServer(IServer server) {
        this.server = server;
        this.populateTable();
    }

    public void setId(Integer id){ this.id=id; }

    private void populateTable() {
        this.idParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.numeParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("numeParticipant"));
        this.varstaParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("varstaParticipant"));
        
        this.tableView.setItems(this.getAll());
    }

    private ObservableList<Participant> getAll() {
        ObservableList<Participant> participants= FXCollections.observableArrayList();
        Iterable<Participant> lista = server.getAllParticipants(String.valueOf(this.id));
        for(Participant p:lista)
            participants.add(p);

        return participants;
    }

    public void handleBack() { this.primaryStage.close(); }
}

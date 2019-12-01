package controllers;

import domain.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceInscriere;
import service.ServiceParticipant;
import service.ServiceProba;

public class ControllerCautare {

    private ServiceParticipant serviceParticipant;
    private ServiceInscriere serviceInscriere;
    Integer id;

    Stage primaryStage;

    @FXML
    private TableView<Participant> tableView;
    @FXML
    private TableColumn<Participant,Integer> idParticipantColumn;
    @FXML
    private TableColumn<Participant,String> numeParticipantColumn;
    @FXML
    private TableColumn<Participant,Integer> varstaParticipantColumn; 

    public ControllerCautare(){}

    public void setPrimaryStage(Stage secondaryStage) { this.primaryStage = secondaryStage; }
    
    public void setServices(ServiceParticipant serviceParticipant,ServiceInscriere serviceInscriere) {
        this.serviceParticipant = serviceParticipant;
        this.serviceInscriere = serviceInscriere;

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
        for(Participant p:this.serviceParticipant.findAll()){
            Integer temp=Integer.valueOf(String.valueOf(this.id)+String.valueOf(p.getId()));
            if(this.serviceInscriere.findOne(temp)!=null){
                participants.add(p);
            }
        }
        return participants;
    }

    public void handleBack() { this.primaryStage.close(); }
}

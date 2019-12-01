package controllers;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repo.RepoException;
import service.ServiceInscriere;
import service.ServiceParticipant;
import service.ServiceProba;
import validatori.ValidationException;

public class ControllerInscriere {
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

    private ServiceParticipant serviceParticipant;
    private ServiceProba serviceProba;
    private ServiceInscriere serviceInscriere;

    Stage primaryStage;

    public ControllerInscriere(){}

    public void setServices(ServiceParticipant serviceParticipant,
                            ServiceProba serviceProba, ServiceInscriere serviceInscriere) {
        this.serviceParticipant=serviceParticipant;
        this.serviceProba=serviceProba;
        this.serviceInscriere=serviceInscriere;

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
        for(Proba p:this.serviceProba.findAll()){
            probe.add(p);
        }
        return probe;
    }

    public void setPrimaryStage(Stage thirdStage) { this.primaryStage=thirdStage; }

    public void handleInscrie() {
        Integer id = this.serviceParticipant.size()+1;
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

            try{
                if(this.serviceInscriere.findOne(Integer.valueOf(String.valueOf(proba.getId())+ participant.getId()))==null){
                    this.serviceParticipant.save(participant);
                    this.serviceInscriere.save(new Inscriere(proba.getId(), participant.getId()));
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Inscrierea exista deja!");
                    alert.showAndWait();
                }
            } catch (ValidationException | RepoException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                }
            }
    }

    public void handleBack() { this.primaryStage.close(); }
}
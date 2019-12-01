package controllers;

import domain.Distanta;
import domain.Proba;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ServiceInscriere;
import service.ServiceParticipant;
import service.ServiceProba;
import utils.ProbaExtins;
import java.io.IOException;

public class ControllerMainStage {
    private ServiceParticipant serviceParticipant;
    private ServiceProba serviceProba;
    private ServiceInscriere serviceInscriere;

    private ControllerCautare controllerCautare;
    private ControllerInscriere controllerInscriere;

    Stage primaryStage;
    Stage secondaryStage;
    Stage thirdStage;

    @FXML
    private TableView<ProbaExtins> tableView;
    @FXML
    private TableColumn<ProbaExtins, Integer> idProbaColumn;
    @FXML
    private TableColumn<ProbaExtins,String> stilProbaColumn;
    @FXML
    private TableColumn<ProbaExtins,Integer> distantaProbaColumn;
    @FXML
    private TableColumn<ProbaExtins,Integer> nrInscrisiColumn;

    public ControllerMainStage(){}

    public void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    public void setServices(ServiceParticipant serviceParticipant,
                            ServiceProba serviceProba, ServiceInscriere serviceInscriere){
        this.serviceParticipant=serviceParticipant;
        this.serviceProba=serviceProba;
        this.serviceInscriere=serviceInscriere;

        this.populateTable();
    }

    public void populateTable(){
        this.idProbaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.stilProbaColumn.setCellValueFactory(new PropertyValueFactory<>("stil"));
        this.distantaProbaColumn.setCellValueFactory(new PropertyValueFactory<>("DistantaInt"));
        this.nrInscrisiColumn.setCellValueFactory(new PropertyValueFactory<>("nrInscrisi"));

        this.tableView.setItems(this.getAll());
    }

    private ObservableList<ProbaExtins> getAll() {
        ObservableList<ProbaExtins> probe= FXCollections.observableArrayList();
        for(Proba p:this.serviceProba.findAll()){
            int nr=this.serviceProba.numarInscrieri(p.getId());
            ProbaExtins pe=new ProbaExtins(p.getId(),Distanta.getValueInt(p.getDistanta().toString()),
                    p.getStil().toString(),nr);
            probe.add(pe);
        }
        return probe;
    }

    public void handleCauta() throws IOException {
        if(this.tableView.getSelectionModel().getSelectedIndex()<0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nicio proba selectata :(");
            alert.showAndWait();
        }
        else{
            ProbaExtins probaExtins = this.tableView.getSelectionModel().getSelectedItem();
            Integer idProba = probaExtins.getId();

            this.secondaryStage = new Stage();
            AnchorPane cautaLayout = getCautareLayout(idProba);

            Scene scene = new Scene(cautaLayout);
            this.secondaryStage.setScene(scene);
            this.controllerCautare.setPrimaryStage(this.secondaryStage);
            this.secondaryStage.setTitle("Participanti inscrisi");
            this.secondaryStage.show();
        }

    }

    public void handleInscriere() throws IOException {
        this.thirdStage=new Stage();
        AnchorPane inscriereLayout=getInscriereLayout();
        Scene scene = new Scene(inscriereLayout);
        this.thirdStage.setScene(scene);
        this.controllerInscriere.setPrimaryStage(this.thirdStage);
        this.thirdStage.setTitle("Inscriere participant");
        this.thirdStage.show();
    }

    public void handleLogout(ActionEvent actionEvent) { this.primaryStage.close(); }

    private AnchorPane getCautareLayout(Integer id) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/cautare.fxml"));
        AnchorPane cautareLayout = loader.load();

        controllerCautare=loader.getController();
        controllerCautare.setId(id);
        controllerCautare.setServices(serviceParticipant,serviceInscriere);

        return cautareLayout;
    }

    private AnchorPane getInscriereLayout() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/inscriere.fxml"));
        AnchorPane inscriereLayout = loader.load();

        controllerInscriere=loader.getController();
        controllerInscriere.setServices(serviceParticipant,serviceProba,serviceInscriere);

        return inscriereLayout;
    }
}

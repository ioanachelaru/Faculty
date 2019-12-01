package controllers;

import Model.Angajat;
import Model.Distanta;
import Model.Proba;
import Model.ProbaExtins;
import Services.IObserver;
import Services.IServer;
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
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerMainStage extends UnicastRemoteObject implements IObserver {

    private ControllerCautare controllerCautare;
    private ControllerInscriere controllerInscriere;

    Stage primaryStage;
    Stage secondaryStage;
    Stage thirdStage;
    private IServer server;

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

    private ObservableList<ProbaExtins> probaExtins = FXCollections.observableArrayList();

    public ControllerMainStage() throws RemoteException {
        super();
    }

    void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    void setServer(IServer server) {
        this.server = server;
        probaExtins.addAll(server.getAllProbaExtins());
        this.populateTable();
    }

    private void populateTable(){
        this.idProbaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.stilProbaColumn.setCellValueFactory(new PropertyValueFactory<>("stil"));
        this.distantaProbaColumn.setCellValueFactory(new PropertyValueFactory<>("DistantaInt"));
        this.nrInscrisiColumn.setCellValueFactory(new PropertyValueFactory<>("nrInscrisi"));

        this.tableView.setItems(probaExtins);
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
            this.controllerCautare.setServer(this.server);
            this.secondaryStage.setTitle("Participanti inscrisi");
            this.secondaryStage.show();
        }

    }

    public void handleInscriere() throws IOException {
        this.thirdStage=new Stage();
        AnchorPane inscriereLayout = getInscriereLayout();

        Scene scene = new Scene(inscriereLayout);
        this.thirdStage.setScene(scene);
        this.controllerInscriere.setPrimaryStage(this.thirdStage);
        this.controllerInscriere.setServer(this.server);
        this.thirdStage.setTitle("Inscriere participant");
        this.thirdStage.show();
    }

    public void handleLogout() {
        Angajat dummy = new Angajat("user","pass");

        this.server.logout(dummy,this);
        this.primaryStage.close();
    }

    private AnchorPane getCautareLayout(Integer id) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/cautare.fxml"));
        AnchorPane cautareLayout = loader.load();

        controllerCautare = loader.getController();
        controllerCautare.setId(id);

        return cautareLayout;
    }

    private AnchorPane getInscriereLayout() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/inscriere.fxml"));
        AnchorPane inscriereLayout = loader.load();

        controllerInscriere = loader.getController();
        return inscriereLayout;
    }

    @Override
    public void update(Iterable<ProbaExtins> probe) {
        probaExtins.setAll(StreamSupport.stream(probe.spliterator(),false)
                .collect(Collectors.toList()));
        this.tableView.setItems(probaExtins);
    }
}

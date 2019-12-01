package controllers;

import domain.Abonat;
import domain.Carte;
import domain.Exemplar;
import domain.Imprumut;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceCarte;
import services.ServiceExemplar;
import services.ServiceImprumut;
import utils.Event;
import utils.Observer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerAbonat implements Observer<Event> {

    public TableView<Carte> cartiTable;

    public TableColumn<Carte, Integer> idColumn;
    public TableColumn<Carte, String> titluColumn;
    public TableColumn<Carte, String> autorColumn;

    private ServiceCarte serviceCarte;
    private ServiceExemplar serviceExemplar;
    private ServiceImprumut serviceImprumut;

    private ObservableList<Carte> listaCarti = FXCollections.observableArrayList();

    private Abonat abonat;

    private Stage primaryStage;

    void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    public void setAbonat(Abonat abonat) { this.abonat = abonat; }

    public void setServices(ServiceCarte serviceCarte, ServiceExemplar serviceExemplar,
                            ServiceImprumut serviceImprumut){
        this.serviceCarte = serviceCarte;

        this.serviceCarte.addObserver(this);

        this.serviceExemplar = serviceExemplar;
        this.serviceImprumut = serviceImprumut;
        this.serviceImprumut.addObserver(this);
        this.listaCarti.addAll((List) this.findAllBooksAvailable());
        this.initTable();
    }

    private void initTable(){
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        this.autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));

        this.cartiTable.setItems(this.listaCarti);
    }

    private Iterable<Carte> findAllBooksAvailable(){
        Iterable<Carte> result = new ArrayList<>();
        Iterable<Carte> carteIterable = this.serviceCarte.findAllBooksAvailable();
        Iterable<Exemplar> exemplarIterable = this.serviceExemplar.findAll();

        for (Carte c:carteIterable)
            for (Exemplar e:exemplarIterable) {
                if(e.getIdCarte() == c.getId() && e.getDisponibil().equals("disponibil")){
                    ((ArrayList<Carte>) result).add(c);
                    break;
                }
            }
        return result;
    }

    private void populateTable(){
        Iterable<Carte> carteIterable = this.findAllBooksAvailable();
        this.cartiTable.setItems(FXCollections.observableArrayList((List)carteIterable));
    }

    private void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("");
        alert.setContentText(s);
        alert.show();
    }

    public void handleImprumuta(ActionEvent actionEvent) {
        int index = this.cartiTable.getSelectionModel().getSelectedIndex();
        if (index < 0){
            this.showError("Mai intai trebuie sa selectezi o carte!");
            return;
        }
        else {
            Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();
            boolean ok = true;

            //verifica daca aceasta carte a mai fost imprumutata de acest abonat
            List<Exemplar> exemplarList = (List<Exemplar>) this.serviceExemplar.findAll();
            List<Exemplar> exemplare = new ArrayList<>();
            for (Exemplar e:exemplarList) {
                if(e.getIdCarte() == carte.getId() && e.getDisponibil().equals("indisponibil"))
                    exemplare.add(e);
            }

            List<Imprumut> imprumutList = (List<Imprumut>) this.serviceImprumut.findAll();
            for (Imprumut i:imprumutList)
                for (Exemplar e:exemplare)
                    if(e.getIdCarte() == carte.getId() && this.abonat.getCui().equals(i.getCuiAbonat())
                            && i.getActiv().equals("da")) {
                        ok = false;
                        this.showError("Se pare ca aveti deja aceasta carte.");
                        break;
                    }
            if(ok) {

                // cauta un exemplar disponibil
                Exemplar exemplar = new Exemplar();
                for (Exemplar e : exemplarList) {
                    if (e.getIdCarte() == carte.getId())
                        if (e.getDisponibil().equals("disponibil")) {
                            exemplar = e;
                            break;
                        }
                }

                // gets the current date
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                Imprumut imprumut = new Imprumut(this.serviceImprumut.getId(), this.abonat.getCui(),
                        exemplar.getId(), "bibli1", date.format(formatter));

                exemplar.setDisponibil("indisponibil");
                this.serviceExemplar.update(exemplar);

                this.serviceImprumut.save(imprumut);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmare");
                alert.setHeaderText("");
                alert.setContentText("Imprumut realizat cu succes!");
                alert.show();
            }
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        this.primaryStage.close();
    }

    @Override
    public void update(Event event) { this.populateTable(); }
}

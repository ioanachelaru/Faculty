package controllers;

import domain.Carte;
import domain.Exemplar;
import domain.Imprumut;
import exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import services.ServiceAbonat;
import services.ServiceCarte;
import services.ServiceExemplar;
import services.ServiceImprumut;
import utils.Event;
import utils.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerBibliotecar implements Observer<Event> {

    public TableView<Carte> cartiTable;

    public TableColumn<Carte, Integer> idColumn;
    public TableColumn<Carte, String> titluColumn;
    public TableColumn<Carte, String> autorColumn;
    public TableColumn<Carte, Integer> nrExemplareColumn;

    public TextField cuiAbonatTextField;
    
    public TextField titluTextField;
    public TextField autorTextField;

    private ServiceAbonat serviceAbonat;
    private ServiceCarte serviceCarte;
    private ServiceExemplar serviceExemplar;
    private ServiceImprumut serviceImprumut;

    private Stage primaryStage;
    private Stage secondaryStage;

    private ObservableList<Carte> listaCarti = FXCollections.observableArrayList();

    void setPrimaryStage(Stage primaryStage){ this.primaryStage=primaryStage; }

    @SuppressWarnings("unchecked")
    void setServices(ServiceAbonat serviceAbonat, ServiceCarte serviceCarte,
                     ServiceExemplar serviceExemplar,ServiceImprumut serviceImprumut){
        this.serviceAbonat = serviceAbonat;
        this.serviceCarte = serviceCarte;
        this.serviceExemplar = serviceExemplar;
        this.serviceImprumut = serviceImprumut;

        this.serviceCarte.addObserver(this);

        this.listaCarti.addAll((List) this.serviceCarte.findAll());
        this.setEditableTable();
        this.initTable();
    }

    private void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        this.autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        this.nrExemplareColumn.setCellValueFactory(new PropertyValueFactory<>("nrExemplare"));

        this.titluColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.autorColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.cartiTable.setItems(this.listaCarti);
    }

    private void populateTable() {
        Iterable<Carte> listCarti = this.serviceCarte.findAll();

        this.cartiTable.setItems(FXCollections.observableArrayList((List)listCarti));
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
        String titlu = this.titluTextField.getText();
        String autor = this.autorTextField.getText();

        if(titlu.equals("") || autor.equals(""))
            this.showError("Date invalide!");
        else{
            Carte carte = new Carte(this.serviceCarte.getId(),titlu,autor);
            this.serviceCarte.save(carte);
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        int index = this.cartiTable.getSelectionModel().getSelectedIndex();
        if (index < 0){
            this.showError("Mai intai trebuie sa selectezi o carte!");
            return;
        }
        Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();
        this.serviceExemplar.deleteByBook(carte.getId());
        this.serviceCarte.delete(carte.getId());
    }

    private TableColumn <Carte, ?> getTableColumn(
            final TableColumn <Carte, ?> column, int offset) {
        int columnIndex = this.cartiTable.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return this.cartiTable.getVisibleLeafColumn(newColumnIndex);
    }

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition<Carte, ?> focusedCell = this.cartiTable
                .focusModelProperty().get().focusedCellProperty().get();
        this.cartiTable.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void selectPrevious() {
        if (this.cartiTable.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from
            // right-to-left, and then wrapping to the end of the previous line
            TablePosition <Carte, ? > pos = this.cartiTable.getFocusModel().getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                this.cartiTable.getSelectionModel().select(pos.getRow(),
                        getTableColumn(pos.getTableColumn(), -1));
            } else if (pos.getRow() < this.cartiTable.getItems().size()) {
                // wrap to end of previous row
                this.cartiTable.getSelectionModel().select(pos.getRow() - 1,
                        this.cartiTable.getVisibleLeafColumn(
                                this.cartiTable.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = this.cartiTable.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                this.cartiTable.getSelectionModel().select(this.cartiTable.getItems().size() - 1);
            } else if (focusIndex > 0) {
                this.cartiTable.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    public void handleEditTitlu(TableColumn.CellEditEvent<Carte, String> carteStringCellEditEvent) {
        try {
            Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();
            carte.setTitlu(carteStringCellEditEvent.getNewValue());
            this.serviceCarte.update(carte);
        }catch (ValidationException e){
            this.showError(e.getMessage());
        }
    }

    public void handleEditAutor(TableColumn.CellEditEvent<Carte, String> carteStringCellEditEvent) {
        try {
            Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();
            carte.setAutor(carteStringCellEditEvent.getNewValue());
            this.serviceCarte.update(carte);
        }catch (ValidationException e){
            this.showError(e.getMessage());
        }
    }

    private void setEditableTable() {
        this.cartiTable.setEditable(true);
        // allows the individual cells to be selected
        this.cartiTable.getSelectionModel().cellSelectionEnabledProperty().set(true);

        this.cartiTable.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (event.getCode() == KeyCode.RIGHT ||
                    event.getCode() == KeyCode.TAB) {
                this.cartiTable.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                // work around due to
                // TableView.getSelectionModel().selectPrevious() due to a bug
                // stopping it from working on
                // the first column in the last row of the table
                selectPrevious();
                event.consume();
            }
        });
    }

    public void returnBook(ActionEvent actionEvent) {
        int index = this.cartiTable.getSelectionModel().getSelectedIndex();
        if (index < 0){
            this.showError("Mai intai trebuie sa selectezi o carte!");
            return;
        }
        boolean imprumutat = false;

        Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();
        List<Exemplar> exemplarList = (List<Exemplar>) this.serviceExemplar.findAll();
        List<Exemplar> exemplare = new ArrayList<>();
        for (Exemplar e:exemplarList) {
            if(e.getIdCarte() == carte.getId() && e.getDisponibil().equals("indisponibil"))
                exemplare.add(e);
        }

        List<Imprumut> imprumutList = (List<Imprumut>) this.serviceImprumut.findAll();

        for (Imprumut i:imprumutList)
            for (Exemplar e:exemplare)
                if(e.getIdCarte() == carte.getId() && this.cuiAbonatTextField.getText().equals(i.getCuiAbonat())
                        && i.getActiv().equals("da")) {
                    imprumutat = true;

                    e.setDisponibil("disponibil");
                    this.serviceExemplar.update(e);

                    i.setActiv("nu");
                    this.serviceImprumut.update(i);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmare");
                    alert.setHeaderText("");
                    alert.setContentText("Cartea a fost returnata cu succes!");
                    alert.show();

                    break;
                }
        if(!imprumutat)
            this.showError("Abonatul nu a imprumutat aceasta carte!");
    }

    public void handleAddExemplar(ActionEvent actionEvent) throws IOException {
        int index = this.cartiTable.getSelectionModel().getSelectedIndex();
        if (index < 0){
            this.showError("Mai intai trebuie sa selectezi o carte!");
            return;
        }
        else{
            Carte carte = this.cartiTable.getSelectionModel().getSelectedItem();

            this.secondaryStage = new Stage();
            AnchorPane abonatLayout = this.getLayoutAddExemplar(carte);
            Scene scene = new Scene(abonatLayout);
            this.secondaryStage.setScene(scene);
            this.secondaryStage.setResizable(false);
            this.secondaryStage.setTitle("User: bibliotecar");
            this.secondaryStage.show();
        }
    }

    private AnchorPane getLayoutAddExemplar(Carte carte) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/addExemplare.fxml"));
        AnchorPane panelLayout = loader.load();

        ControllerAddExemplare controllerAddExemplare = loader.getController();
        controllerAddExemplare.setServices(this.serviceCarte, this.serviceExemplar, carte);
        controllerAddExemplare.setPrimaryStage(this.secondaryStage);

        return panelLayout;
    }

    public void handleLogout(ActionEvent actionEvent) { this.primaryStage.close(); }

    @Override
    public void update(Event event) { this.populateTable(); }
}
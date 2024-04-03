package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.graphicalUserInterface.Item.ManagerTable;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerGraphicalUserInterface extends UserGraphicalUserInterface implements IManagerUserInterface {
    @FXML
    public Label checkCurrentBidsVBoxTitle;
    @FXML
    public Label labelVBoxTitle;
    @FXML
    private TableView<ManagerTable> auctionsTableView;
    @FXML
    private TableColumn<ManagerTable, String> nameColumn;
    @FXML
    private TableColumn<ManagerTable, String> descriptionColumn;
    @FXML
    private TableColumn<ManagerTable, String> startDateColumn;
    @FXML
    private TableColumn<ManagerTable, String> endDateColumn;
    @FXML
    private TableColumn<ManagerTable, String> statusColumn;

    private static ManagerGraphicalUserInterface instance = null;

    public static ManagerGraphicalUserInterface getInstance() {
        if (instance == null) {
            throw new NullPointerException("Instance non initialisée");
        }
        return instance;
    }

    public static void setInstance(ManagerGraphicalUserInterface instance) {
        ManagerGraphicalUserInterface.instance = instance;
    }

    public void initialize() {
        checkCurrentBidsVBoxTitle.setVisible(false);
        checkCurrentBidsVBoxTitle.setManaged(false);
        auctionsTableView.setVisible(false);
        auctionsTableView.setManaged(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("debut"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("fin"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        for (TableColumn<?, ?> column : auctionsTableView.getColumns()) {
            column.setPrefWidth(auctionsTableView.getWidth() / auctionsTableView.getColumns().size());
        }
        auctionsTableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            for (TableColumn<?, ?> column : auctionsTableView.getColumns()) {
                column.setPrefWidth(auctionsTableView.getWidth() / auctionsTableView.getColumns().size());
            }
        });
    }


    public void displayHello() {
        addLogMessage("Bienvenue Manager");
    }

    public void displayNewBid(Bid bid) {
        if (!auctionsTableView.isVisible() || !auctionsTableView.isManaged()) {
            auctionsTableView.setVisible(true);
            auctionsTableView.setManaged(true);
            checkCurrentBidsVBoxTitle.setVisible(true);
            checkCurrentBidsVBoxTitle.setManaged(true);
        }
        addLogMessage("Nouvelle enchère reçue : " + bid.getName() + " (" + bid.getId() + ") Date:" + bid.getStartDateTime().toString());
        auctionsTableView.getItems().add(new ManagerTable(bid.getId(), bid.getName(), bid.getDescription(), bid.getStartDateTime().toString(), bid.getEndDateTime().toString(), "En cours..."));
    }

    public void diplayEndBid(String idBid) {
        addLogMessage("L'enchère " + idBid + " a été résolue.");
        for (ManagerTable item : auctionsTableView.getItems()) {
            if (item.getId().equals(idBid)) {
                item.statusProperty().set("Fini");
                break;
            }
        }

    }

    @Override
    public void tellSignatureConfigSetup() {
        addLogMessage("Mise en place de la configuration de la signature...");
    }

    @Override
    public void tellSignatureConfigGeneration() {
        addLogMessage("Génération de la configuration de la signature ...");
    }

    @Override
    public void tellSignatureConfigReady() {
        addLogMessage("Configuration de la signature terminée.");
    }

    @Override
    public void tellManagerReadyToProcessBids() {
        addLogMessage("Gestionnaire prêt à traiter des enchères");
    }

    @Override
    public void tellBidRequest(){addLogMessage("Un enchérisseur a demandé les enchères actuelles.");}
}

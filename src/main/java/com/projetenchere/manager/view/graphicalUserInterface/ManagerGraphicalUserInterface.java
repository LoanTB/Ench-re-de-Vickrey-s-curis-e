package com.projetenchere.manager.view.graphicalUserInterface;

import com.projetenchere.common.model.Bid;
import com.projetenchere.common.view.UserGraphicalUserInterface;
import com.projetenchere.manager.view.IManagerUserInterface;
import com.projetenchere.manager.view.graphicalUserInterface.item.ManagerTable;
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

    @Override
    public void displayHello() {
        addLogMessage("Bienvenue manager");
    }

    @Override
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

    @Override
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
    public synchronized void displayBidderAskBids(){
        addLogMessage("Un enchérisseur a demandé les enchères actuelles.");
    }
    @Override
    public void displaySendBidderPubKey(){
        addLogMessage("Envoi des informations de sécurité.");
    }

    @Override
    public void tellKeysGeneration(){
        addLogMessage("Génération des clés...");
    }

    @Override
    public void tellKeysReady(){
        addLogMessage("Paire de clés prêtes.");
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
    public void tellFalsifiedSignatureBidder() {
        addLogMessage("Signature de l'enchérisseur usurpée.");
    }

    @Override
    public void tellFalsifiedSignatureSeller() {
        addLogMessage("Signature du vendeur usurpée.");
    }


    @Override
    public void tellBidRequest(){addLogMessage("Un enchérisseur a demandé les enchères actuelles.");}
}

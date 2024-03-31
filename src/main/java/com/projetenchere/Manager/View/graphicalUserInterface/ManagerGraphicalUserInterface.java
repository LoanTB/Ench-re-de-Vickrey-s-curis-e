package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
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
    private TableView<ItemManagerTable> auctionsTableView;
    @FXML
    private TableColumn<ItemManagerTable, String> nameColumn;
    @FXML
    private TableColumn<ItemManagerTable, String> descriptionColumn;
    @FXML
    private TableColumn<ItemManagerTable, String> startDateColumn;
    @FXML
    private TableColumn<ItemManagerTable, String> endDateColumn;
    @FXML
    private TableColumn<ItemManagerTable, String> statusColumn;

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
        auctionsTableView.getItems().add(new ItemManagerTable(bid.getId(), bid.getName(), bid.getDescription(), bid.getStartDateTime().toString(), bid.getEndDateTime().toString(), "En cours..."));
    }

    public void diplayEndBid(String idBid) {
        addLogMessage("L'enchère " + idBid + " a été résolue.");
        for (ItemManagerTable item : auctionsTableView.getItems()) {
            if (item.getId().equals(idBid)) {
                item.statusProperty().set("Fini");
                break;
            }
        }

    }

    public void displayBidderAskBids(){
        addLogMessage("Un enchérisseur a demandé les enchères actuelles.");
    }
    public void displaySendBidderPubKey(){
        addLogMessage("Envoi des informations de sécurité.");
    }

    public void displayErrorSignatureFalsified(String user){
        addLogMessage("Un "+user+" a tenté de falsifier les enchères."); //TODO : Utiliser d'autres messages du genre lors des vérifications de signature.
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


}

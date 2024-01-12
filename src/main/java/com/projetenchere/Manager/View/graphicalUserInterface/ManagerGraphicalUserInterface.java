package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

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
    public void displayGenerateKey() {
        addLogMessage("Génération de clé...");
    }

    @Override
    public void displayBidLaunch() {
        addLogMessage("Lancement de l'enchère...");
    }

    @Override
    public void displayReceivedPrices() {
        addLogMessage("Réception des prix...");
    }

    @Override
    public void displayPriceProcessing() {
        addLogMessage("Traitement des prix...");
    }

    @Override
    public void displaySentWinnerPrice() {
        addLogMessage("Envoie du prix gagnant...");
    }

    @Override
    public void displayEndOfAuction() {
        addLogMessage("Fin des echères !");
    }

    @Override
    public String askBidName() {
        return null;
    }

    @Override
    public String askBidDescription() {
        return null;
    }

    @Override
    public LocalDateTime askBidEndTime() {
        return null;
    }

    @Override
    public String askSellerAddress() {
        return null;
    }

    @Override
    public void displayWinnerPrice(Winner winner) {
        addLogMessage("Le prix gagnant de l'enchère " + winner.bidId() + " à été déterminé et répondu (" + winner.price() + "€)");
    }

    @Override
    public void tellConnectingNewSeller(String id) {
        addLogMessage("Connexion avec un nouveau vendeur (" + id + ")");
    }

    @Override
    public void tellConnectingNewBidder(String id) {
        addLogMessage("Connexion d'un nouvel enchérisseur (" + id + "), notification des vendeurs pour prévenir d'un nouvel enchérisseur potentiel");
    }

    @Override
    public void tellRequestInformationAboutBiddersBySeller(String id) {
        addLogMessage("Demande des informations sur les enchérisseurs actuellement connectés par le vendeur " + id);
    }

    @Override
    public void tellRequestCurrentBidsByBidder(String id) {
        addLogMessage("Demande des enchères actuelles par l'enchérisseur " + id);
    }

    @Override
    public void tellRequestToDetermineTheWinnerOfBidBySeller(String idBid, String idSeller) {
        addLogMessage("Demande de détermination du gagnant de l'enchère " + idBid + " par le vendeur " + idSeller);
    }

    @Override
    public void tellManagerReadyToProcessBids() {
        addLogMessage("Gestionnaire prêt à traiter des enchères");
    }

    @Override
    public void tellBidReceivedby(String idSeller, String idBid) {
        addLogMessage("Enchère reçu (" + idBid + ") créé par le vendeur " + idSeller);
    }

    @Override
    public void tellReceivingAndReplyToInformationOf(String id, String type) {
        addLogMessage("Etablissement d'une connexion sécurisé avec un nouvel " + type + " (" + id + ")");
    }
}

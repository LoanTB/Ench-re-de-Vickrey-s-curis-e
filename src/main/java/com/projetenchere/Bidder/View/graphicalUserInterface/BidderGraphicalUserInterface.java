package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BidderGraphicalUserInterface extends UserGraphicalUserInterface implements IBidderUserInterface {

    private Offer offer = null;

    @FXML
    public Label textAskPrice;
    @FXML
    public Button buttonCreate;
    @FXML
    private TableView<ItemBidderTable> auctionsTableView;
    @FXML
    private TableColumn<ItemBidderTable, String> nameColumn;
    @FXML
    private TableColumn<ItemBidderTable, String> descriptionColumn;
    @FXML
    private TableColumn<ItemBidderTable, String> endDateColumn;

    @FXML
    private TextField offerAmountTextField;
    @FXML
    private Button submitOfferButton;

    private CurrentBids currentBids;

    private Bid selectedBid;

    private Bidder bidder = null;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("fin"));
        auctionsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0 && auctionsTableView.getSelectionModel().getSelectedItem() != null) {
                selectedBid = currentBids.getBid(auctionsTableView.getSelectionModel().getSelectedItem().getId());
            }
        });
    }

    @Override
    public void displayBid(CurrentBids currentBids) {
        this.currentBids = currentBids;
        List<ItemBidderTable> itemBidderTables = new ArrayList<>();
        for (Bid bid:currentBids.getCurrentBids()){
            itemBidderTables.add(new ItemBidderTable(bid.getId(),bid.getName(), bid.getDescription(), bid.getEndDateTime().toString()));
        }
        auctionsTableView.getItems().addAll(itemBidderTables);
    }

    @FXML
    private void handleCreateBidButton() {
        if (selectedBid == null){
            addLogMessage("Erreur : Vous devez sélectionner une enchère");
        } else if (offerAmountTextField.getText().isEmpty()){
            addLogMessage("Erreur : Vous devez entrer un prix");
        } else if (Double.parseDouble(offerAmountTextField.getText()) <= 0) {
            addLogMessage("Erreur : Vous devez entrer un prix plus grand que 0");
        } else if (bidder == null){
            addLogMessage("Erreur : Ce n'est pas encore le moment d'enchérrir");
        } else {
            auctionsTableView.setVisible(false);
            offerAmountTextField.setVisible(false);
            buttonCreate.setVisible(false);
            buttonCreate.setVisible(false);
            textAskPrice.setVisible(false);
            offer = new Offer(bidder.getSignature(),selectedBid.getId(),offerAmountTextField.getText());
        }
    }

    @Override
    public Offer readOffer(Bidder bidder, CurrentBids currentBids) {
        this.bidder = bidder;
        while (offer == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return offer;
    }


    @Override
    public void tellSignatureConfigSetup(){
        addLogMessage("Mise en place de la configuration de la signature...");
    }
    @Override
    public void tellSignatureConfigGeneration(){
        addLogMessage("Génération de la configuration de la signature ...");
    }
    @Override
    public void tellSignatureConfigReady(){
        addLogMessage("Configuration de la signature terminée.");
    }

    @Override
    public void tellOfferWon(double priceToPay) {
        addLogMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }

    @Override
    public void tellOfferLost() {
        addLogMessage("Votre offre a perdu");
    }

    @Override
    public void tellOfferSent() {
        addLogMessage("Votre offre a bien été envoyé");
    }

    @Override
    public void tellWaitOfferResult() {
        addLogMessage("Attente des résultats...");
    }

    @Override
    public void tellWaitBidsAnnoncement() {
        addLogMessage("Attente de reception des enchères en cours...");
    }

    @Override
    public void tellWaitBidsPublicKeysAnnoncement() {
        addLogMessage("Attente/Vérification de réception des clés des enchères en cours...");
    }

    @Override
    public String readName() {
        return null;
    }

    @Override
    public String readSurname() {
        return null;
    }

    @Override
    public int readPort() {
        return 0;
    }

    @Override
    public void tellWaitManager() {

    }

    @Override
    public void tellManagerFound() {

    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        addLogMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void displayHello() {
        addLogMessage("Bienvenue Enchérisseur !");
    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {
        addLogMessage("Reception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfCurrentBids() {
        addLogMessage("Reception des enchères actuelles");
    }

    @Override
    public void tellReceiptOfEncryptionKeysForCurrentBids() {
        addLogMessage("Réception des clés de chiffrement des enchères actuelles");
    }

    @Override
    public void tellReceiptOfBidResult(String id) {
        addLogMessage("Réception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendRequestOffers() {
        addLogMessage("Envoie de la demande d'enchères actuelles au gestionnaire");
    }
}

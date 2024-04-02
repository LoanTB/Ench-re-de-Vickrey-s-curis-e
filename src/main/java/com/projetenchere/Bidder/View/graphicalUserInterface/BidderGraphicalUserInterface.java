package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BidderGraphicalUserInterface extends UserGraphicalUserInterface implements IBidderUserInterface {

    @FXML
    public Label checkCurrentBidsVBoxTitle;
    @FXML
    public Label labelVBoxTitle;
    @FXML
    public Button refreshButton;
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
    private Offer offer = null;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("fin"));
        auctionsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0 && auctionsTableView.getSelectionModel().getSelectedItem() != null) {
                selectedBid = currentBids.getBid(auctionsTableView.getSelectionModel().getSelectedItem().getId());
            }
        });
        refreshButton.setOnAction(actionEvent -> {
            try {
                BidderAppLoader.getControllerInstance().initWithManager();
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
        });
        for (TableColumn<?, ?> column : auctionsTableView.getColumns()) {
            column.setPrefWidth(auctionsTableView.getWidth() / auctionsTableView.getColumns().size());
        }
        auctionsTableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            for (TableColumn<?, ?> column : auctionsTableView.getColumns()) {
                column.setPrefWidth(auctionsTableView.getWidth() / auctionsTableView.getColumns().size());
            }
        });

        offerAmountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    String value = newValue.replaceAll("[^\\d.]", "");
                    offerAmountTextField.setText(value);
                }
                if (offerAmountTextField.getText().length() > 10) {
                    String s = offerAmountTextField.getText().substring(0, 10);
                    offerAmountTextField.setText(s);
                }
            }
        });

    }

    @Override
    public void displayBid(CurrentBids currentBids) {
        boolean ok = true;
        this.currentBids = currentBids;
        List<ItemBidderTable> itemBidderTables = new ArrayList<>();
        for (Bid bid : currentBids.getCurrentBids()) {
            if (auctionsTableView.getItems() != null) {
                for (ItemBidderTable item : auctionsTableView.getItems()) {
                    if (item.getId().equals(bid.getId())) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                itemBidderTables.add(new ItemBidderTable(bid.getId(), bid.getName(), bid.getDescription(), bid.getEndDateTime().toString()));
            }

        }
        auctionsTableView.getItems().addAll(itemBidderTables);
    }

    @FXML
    private void handleCreateBidButton() {
        boolean correctDoule;
        try {
            Double.parseDouble(offerAmountTextField.getText());
            correctDoule = true;
        } catch (Exception ignore) {
            correctDoule = false;
        }
        if (selectedBid == null) {
            addLogMessage("Erreur : Vous devez sélectionner une enchère");
        } else if (offerAmountTextField.getText().isEmpty()) {
            addLogMessage("Erreur : Vous devez entrer un prix");
        } else if (!correctDoule) {
            addLogMessage("Erreur : Le prix doit être un nombre correcte");
        } else if (offerAmountTextField.getText().length() > 10) {
            addLogMessage("Erreur : Le prix est trop grand");
        } else if (Double.parseDouble(offerAmountTextField.getText()) <= 0) {
            addLogMessage("Erreur : Vous devez entrer un prix plus grand que 0");
        } else if (bidder == null) {
            addLogMessage("Erreur : Ce n'est pas encore le moment d'enchérrir");
        } else {
            auctionsTableView.setVisible(false);
            auctionsTableView.setManaged(false);
            offerAmountTextField.setVisible(false);
            offerAmountTextField.setManaged(false);
            buttonCreate.setVisible(false);
            buttonCreate.setManaged(false);
            textAskPrice.setVisible(false);
            textAskPrice.setManaged(false);
            checkCurrentBidsVBoxTitle.setVisible(false);
            checkCurrentBidsVBoxTitle.setManaged(false);
            refreshButton.setVisible(false);
            refreshButton.setManaged(false);
            offer = new Offer(selectedBid.getId(), offerAmountTextField.getText());
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
    public synchronized void tellOfferWon(double priceToPay) {
        addLogMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }

    @Override
    public void tellOfferLost() {
        addLogMessage("Votre offre a perdu");
    }

    @Override
    public void tellOfferSent() {
        addLogMessage("Votre offre a bien été envoyée");
    }


    @Override
    public void tellWaitManagerSecurityInformations() {
        addLogMessage("Demande des informations de sécurité du gestionnaire");
    }

    @Override
    public void displayHello() {
        addLogMessage("Bienvenue Enchérisseur !");
    }

    @Override
    public void tellReceiptOfCurrentBids() {
        addLogMessage("Réception des enchères actuelles");
    }

    @Override
    public void tellFalsifiedSignatureManager() {
        addLogMessage("Signature du gestionnaire falsifiée !");
    }
    @Override
    public void tellFalsifiedSignatureSeller() {
        addLogMessage("Signature du vendeur falsifiée !");
    }

}

package com.projetenchere.Manager.View.commandLineInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

import java.util.List;
import java.util.Scanner;

public class ManagerCommandLineInterface implements IManagerUserInterface{
    public static final Scanner scanner = new Scanner(System.in);

    public ManagerCommandLineInterface(){

    }

    public void showMessage(String message)    {
        System.out.println(message);
    }

    public String readMessage(){
        return scanner.nextLine();
    }

    @Override
    public Bid askBidInformations(){
        String name = askBidName();
        String description = askBidDescription();
        return new Bid(name,description);
    }

    @Override
    public String askBidDescription() {
        showMessage("Veuillez saisir la description de l'enchère : ");
        String description = readMessage();
        return description;
    }

    @Override
    public String askBidName(){
        showMessage("Veuillez saisir le nom de l'enchère : ");
        String name = readMessage();
        return name;
    }

    @Override
    public void displayPrices(List<Double> DecryptedPrice){

    };

    @Override
    public void displayWinnerPrice(){

    };

}

package com.projetenchere.Manager.View.commandLineInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Model.Bid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

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
        LocalDateTime end = askBidEndTime();
        return new Bid(name,description,end);
    }

    private static boolean isValidDateFormat(String value, DateTimeFormatter formatter){
        try{
            LocalDateTime.parse(value,formatter);
            return true;
        }catch(DateTimeParseException e){
            return false;
        }
    }

    @Override
    public LocalDateTime askBidEndTime(){
        boolean checkType = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String dateStr = "";
        while (checkType){
            showMessage("Veuillez saisir la date de fin de l'enchère au format dd-MM-yyyy HH:mm:ss ");
            dateStr = readMessage();
            if(isValidDateFormat(dateStr,formatter)){

                dateTime = LocalDateTime.parse(dateStr,formatter);
                if(dateTime.isAfter(LocalDateTime.now())){
                    checkType=false;
                }
                else{
                    System.err.println("Erreur : La date de fin ne peut pas être dans le passé.");
                }

            }else{
                System.err.println("Erreur : Mauvais format de date saisi.");
            }
        }
        return dateTime;
    }



    @Override
    public String askSellerAdress(){
        showMessage("Veuillez saisir l'addresse du vendeur : ");
        return readMessage();
    }
    @Override
    public String askBidDescription() {
        showMessage("Veuillez saisir la description de l'enchère : ");
        return readMessage();
    }

    @Override
    public String askBidName(){
        showMessage("Veuillez saisir le nom de l'enchère : ");
        return readMessage();
    }

    @Override
    public void displayPrices(Set<Double> AllPrices){
        for(Double price : AllPrices){
            System.out.println(price);
        }
    }

    @Override
    public void displayWinnerPrice(){

    }

}

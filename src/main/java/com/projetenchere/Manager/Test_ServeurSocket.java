package com.projetenchere.Manager;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test_ServeurSocket extends NetworkSpace {
    public static void main(String[] args) {
        getMyIP();
        Test_ObjetTransiterBis objetRecu = (Test_ObjetTransiterBis) recevoirObjet(12345, Test_ObjetTransiterBis.class.getName());
        //int objetRecu = (int) recevoirObjet(12345, int.class.getName());
        System.out.println("Objet reçu : " + objetRecu.toString());
    }

}

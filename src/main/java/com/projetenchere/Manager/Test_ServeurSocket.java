package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Test_ServeurSocket extends NetworkSpace {
    public static void main(String[] args) throws IOException {
        try {
            //Test_ObjetTransiterBis objetRecu = (Test_ObjetTransiterBis) recevoirObjet(12345, Test_ObjetTransiterBis.class.getName());
            int objetRecu = (int) recevoirObjet(12345, Integer.class.getName());
            System.out.println("Objet reçu : " + objetRecu);

        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }


}

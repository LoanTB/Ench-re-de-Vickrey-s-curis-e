package com.projetenchere.Util.backup;


import com.projetenchere.common.Util.NetworkSpace;

public class Test_ServeurSocket extends NetworkSpace {
    public static void main(String[] args) {
        Test_ObjetTransiterBis objetRecu = (Test_ObjetTransiterBis) recevoirObjet(12345, Test_ObjetTransiterBis.class.getName());
        //int objetRecu = (int) recevoirObjet(12345, int.class.getName());
        System.out.println("Objet reçu : " + objetRecu.toString());
    }

}

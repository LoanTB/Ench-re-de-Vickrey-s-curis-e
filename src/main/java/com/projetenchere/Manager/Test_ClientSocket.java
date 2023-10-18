package com.projetenchere.Manager;


public class Test_ClientSocket extends NetworkSpace{
    public static void main(String[] args) {
        String serverAddress = "172.16.214.1";
        int serverPort = 12345;
        Test_ObjetTransiterBis objetEnvoi = new Test_ObjetTransiterBis("le signal", new int[] {1,2});
        int a = 1;
        envoiObjet(serverAddress, serverPort, a);
    }


}

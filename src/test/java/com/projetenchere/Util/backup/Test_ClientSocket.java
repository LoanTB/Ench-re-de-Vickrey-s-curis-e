package com.projetenchere.Util.backup;


import com.projetenchere.common.Util.NetworkSpace;

public class Test_ClientSocket extends NetworkSpace {
    public static void main(String[] args) {
        //String serverIP = "192.168.43.174";
        String serverIP = getMyIP();
        int serverPort = 12345;
        //System.out.println(testPort(serverIP, serverPort));
        System.out.println(serverIP);
        Test_ObjetTransiterBis objetEnvoi = new Test_ObjetTransiterBis("le signal", new int[] {1,2});
        //int a = 1;
        envoiObjet(serverIP, serverPort, objetEnvoi);
    }


}

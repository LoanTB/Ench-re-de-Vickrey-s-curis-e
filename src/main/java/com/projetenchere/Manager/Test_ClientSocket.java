package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Test_ClientSocket extends NetworkSpace{
    public static void main(String[] args) throws IOException {
        String serverAddress = "172.16.214.1";
        int serverPort = 12345;
        Test_ObjetTransiterBis objetEnvoi = new Test_ObjetTransiterBis("le signal", new int[] {1,2});
        int a = 1;
        envoiObjet(serverAddress, serverPort, a);
    }


}

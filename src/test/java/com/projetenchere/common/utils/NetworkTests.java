package com.projetenchere.common.utils;

import com.projetenchere.common.network.*;
import com.projetenchere.common.network.socket.SSLSocketFactory;
import org.jetbrains.annotations.Blocking;
import org.junit.jupiter.api.*;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkTests {
    private static Server server;
    private static Client client;
    private static ClientSocketWrapper clientSocketWrapper;

    @AfterAll
    static void closeConnexion() {
        server.stopAllConnections();
        clientSocketWrapper.close();
    }

    @Test
    @BeforeAll
    @Blocking
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    static void TestStartServerAndClient() throws InterruptedException {
        server = new Server(2048);
        client = new Client();

        server.addHandler(Headers.TEST, new IDataHandler() {
            @Override
            public DataWrapper<String> handle(Serializable data) {
                return new DataWrapper<String>("OK", Headers.TEST);
            }
        });


        server.addHandler(Headers.OK_TEST, new IDataHandler() {
            @Override
            public DataWrapper<StringBuilder> handle(Serializable data) {
                return new DataWrapper<StringBuilder>(new StringBuilder("C'est bien vrais ça"), Headers.TEST);
            }
        });

        server.start();
        while(!server.isReady()){sleep(10, 0);}

        clientSocketWrapper = new ClientSocketWrapper(
                (new SSLSocketFactory()).createSocket(
                        new InetSocketAddress("127.0.0.1", 2048)
                )
        );
        while(!clientSocketWrapper.isConnected()){sleep(10, 0);}
    }

    @Test
    @Order(1)
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void TestSendAndReceiveData() {
        String test = client.fetch(
                clientSocketWrapper,
                Headers.TEST,
                Headers.TEST
        );
        assertEquals("OK", test);
    }

    @Test
    @Order(2)
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void TestRequestBadHeader() {
        assertThrows(RuntimeException.class, () -> {
            client.fetch(
                    clientSocketWrapper,
                    Headers.TEST,
                    Headers.ERROR
            );
        });
    }

    @Test
    @Order(1)
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void TestSendAndRecieveDataWithOtherHeader() {
        StringBuilder test = client.fetch(
                clientSocketWrapper,
                Headers.OK_TEST,
                Headers.TEST
        );
        test.append(". Et ça marhe bien en plus !");
        assertEquals("C'est bien vrais ça. Et ça marhe bien en plus !", test.toString());
    }
}

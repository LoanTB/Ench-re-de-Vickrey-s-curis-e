package com.projetenchere;

import com.projetenchere.common.network.*;
import com.projetenchere.common.network.socket.SSLSocketFactory;
import org.junit.jupiter.api.*;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkTests {
    private static Server server;
    private static Client client;
    private static ClientSocketWrapper clientSocketWrapper;

    @BeforeAll
    static void setupTest() {
        server = new Server(2048);
        client = new Client();
    }

    @AfterEach
    void closeClient() {
        if (clientSocketWrapper != null) {
            //Close socket client
        }
    }

    @Test
    @Order(0)
    @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
    void TestStartServer() throws InterruptedException {
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
        while(!server.isReady()) {sleep(10, 0);}
        clientSocketWrapper = new ClientSocketWrapper(
                (new SSLSocketFactory()).createSocket(
                        new InetSocketAddress("127.0.0.100", 2048)
                )
        );
    }

    @Test
    @Order(1)
    @Timeout(value = 1)
    void TestSendToBadAddress() {
        assertThrows(RuntimeException.class, () -> {
            client.fetch(
                    clientSocketWrapper,
                    Headers.TEST,
                    Headers.TEST
            );
        });
    }

    @Test
    @Order(1)
    @Timeout(value = 1)
    void TestSendToBadPort() {
        assertThrows(RuntimeException.class, () -> {
            client.fetch(
                    clientSocketWrapper,
                    Headers.TEST,
                    Headers.TEST
            );
        });
    }

    @Test
    @Order(1)
    @Timeout(value = 1)
    void TestSendAndReceiveData() {
        String test = client.fetch(
                clientSocketWrapper,
                Headers.TEST,
                Headers.TEST
        );
        assertEquals("OK", test);

    }

    @Test
    @Order(1)
    @Timeout(value = 1)
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
    @Timeout(value = 1)
    void TestSendBadHeader() {
        assertThrows(RuntimeException.class, () -> {
            client.fetch(
                    clientSocketWrapper,
                    Headers.ERROR,
                    Headers.TEST
            );
        });

        assertThrows(RuntimeException.class, () -> {
            client.fetch(
                    clientSocketWrapper,
                    Headers.OK_TEST,
                    Headers.OK_TEST
            );
        });
    }

    @Test
    @Order(1)
    @Timeout(value = 1)
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

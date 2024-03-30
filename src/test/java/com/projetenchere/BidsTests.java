package com.projetenchere;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import com.projetenchere.common.network.Client;
import com.projetenchere.common.network.ClientSocketWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.socket.SSLSocketFactory;
import org.jetbrains.annotations.Blocking;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.net.InetSocketAddress;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BidsTests {
    @Nested
    @DisplayName("Manager")
    @Disabled
    public class TestManager {
        private static ManagerController manager;

        private static Client client;

        private static ClientSocketWrapper clientSocketWrapper;

        @BeforeAll
        @Blocking
        static void setupTest() throws Exception {
            manager = new ManagerController(Mockito.mock());// TODO : Le mock ne marche pas correctement car le code des interface est trop moche
            manager.init();
            clientSocketWrapper = new ClientSocketWrapper(
                    (new SSLSocketFactory()).createSocket(
                            new InetSocketAddress("127.0.0.1", 24683)
                    )
            );
        }

        @Test
        void TestGetPublicKey(){
            assertInstanceOf(PublicKey.class,
                    (new Client()).fetch(
                        clientSocketWrapper,
                        Headers.GET_PUB_KEY,
                        Headers.OK_PUB_KEY
                    )
            );
        }

    }
}

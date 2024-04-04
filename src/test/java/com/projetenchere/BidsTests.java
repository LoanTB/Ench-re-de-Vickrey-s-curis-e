package com.projetenchere;

import com.projetenchere.bidder.controller.BidderController;
import com.projetenchere.common.model.Bid;
import com.projetenchere.common.model.CurrentBids;
import com.projetenchere.common.network.Client;
import com.projetenchere.common.network.ClientSocketWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.socket.SSLSocketFactory;
import com.projetenchere.manager.controller.ManagerController;
import com.projetenchere.manager.loader.ManagerMain;
import com.projetenchere.manager.model.Manager;
import com.projetenchere.seller.controller.SellerController;
import org.jetbrains.annotations.Blocking;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BidsTests {
    @Nested
    @DisplayName("manager")
    public class TestManager {
        private static ManagerController manager;

        private static Client client;

        private static ClientSocketWrapper clientSocketWrapper;

        @BeforeAll
        @Blocking
        @Order(0)
        static void setupTest() throws SignatureException {
            ManagerMain.setViewInstance(Mockito.mock());
            manager = new ManagerController(Mockito.mock());
            manager.init();
            clientSocketWrapper = new ClientSocketWrapper(
                    (new SSLSocketFactory()).createSocket(
                            new InetSocketAddress("127.0.0.1", 24683)
                    )
            );
        }

        @Test
        @Order(1)
        void TestGetPublicKey() {
            assertInstanceOf(PublicKey.class,
                    (new Client()).fetch(
                            clientSocketWrapper,
                            Headers.GET_PUB_KEY,
                            Headers.OK_PUB_KEY
                    )
            );
        }

        @Test
        @Order(1)
        void TestGetCurrentBidsEmpty() {
            assertInstanceOf(CurrentBids.class, (new Client()).fetch(
                    clientSocketWrapper,
                    Headers.GET_CURRENT_BIDS,
                    Headers.OK_CURRENT_BIDS
            ));
        }

        @Test
        @Order(2)
        void TestGet0CurrentBids() {
            CurrentBids currentBids0 = (new Client()).fetch(
                    clientSocketWrapper,
                    Headers.GET_CURRENT_BIDS,
                    Headers.OK_CURRENT_BIDS
            );
            assertEquals(0, currentBids0.getCurrentBids().size());
        }

        @Test
        @Order(1)
        void TestAddBid() throws InterruptedException {
            (new Client()).fetchWithData(clientSocketWrapper, Headers.NEW_BID, Headers.OK_NEW_BID, new Bid("test", "test", "test", LocalDateTime.now().plus(5, ChronoUnit.MINUTES)));
            assertEquals(1, Manager.getInstance().getBids().getCurrentBids().size());
        }

        @Test
        @Order(2)
        void TestGet1CurrentBids() throws InterruptedException {
            (new Client()).fetchWithData(clientSocketWrapper, Headers.NEW_BID, Headers.OK_NEW_BID, new Bid("test", "test", "test", LocalDateTime.now().plus(5, ChronoUnit.MINUTES)));
            CurrentBids currentBids1 = (new Client()).fetch(
                    clientSocketWrapper,
                    Headers.GET_CURRENT_BIDS,
                    Headers.OK_CURRENT_BIDS
            );
            assertEquals(1, currentBids1.getCurrentBids().size());
        }

        @Test
        @Order(2)
        void TestGetCurrentBids() {
            CurrentBids currentBids0 = (new Client()).fetch(
                    clientSocketWrapper,
                    Headers.GET_CURRENT_BIDS,
                    Headers.OK_CURRENT_BIDS
            );
            assertEquals(0, currentBids0.getCurrentBids().size());
            (new Client()).fetchWithData(clientSocketWrapper, Headers.NEW_BID, Headers.OK_NEW_BID, new Bid("test", "test", "test", LocalDateTime.now().plus(5, ChronoUnit.MINUTES)));
            CurrentBids currentBids1 = (new Client()).fetch(
                    clientSocketWrapper,
                    Headers.GET_CURRENT_BIDS,
                    Headers.OK_CURRENT_BIDS
            );
            assertEquals(1, currentBids1.getCurrentBids().size());
        }
    }

    @Nested
    @DisplayName("seller")
    @Disabled
    public class TestSeller {
        private static SellerController seller;

        private static Client client;

        private static ClientSocketWrapper clientSocketWrapper;

        @BeforeAll
        @Blocking
        @Order(0)
        static void setupTest() {
            ManagerMain.setViewInstance(Mockito.mock());
            seller = new SellerController(Mockito.mock());
            clientSocketWrapper = new ClientSocketWrapper(
                    (new SSLSocketFactory()).createSocket(
                            new InetSocketAddress("127.0.0.1", 24682)
                    )
            );
        }
    }

    @Nested
    @DisplayName("bidder")
    @Disabled
    public class TestBidder {
        private static BidderController bidder;

        private static Client client;

        private static ClientSocketWrapper clientSocketWrapper;

        @BeforeAll
        @Blocking
        @Order(0)
        static void setupTest() {
            ManagerMain.setViewInstance(Mockito.mock());
            bidder = new BidderController(Mockito.mock());
            clientSocketWrapper = new ClientSocketWrapper(
                    (new SSLSocketFactory()).createSocket(
                            new InetSocketAddress("127.0.0.1", 24682)
                    )
            );
        }
    }
}

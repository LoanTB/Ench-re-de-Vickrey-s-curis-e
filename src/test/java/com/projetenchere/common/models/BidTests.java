package com.projetenchere.common.models;

import com.projetenchere.common.Models.Bid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.time.LocalDateTime;

public class BidTests {

    @Test
    public void testBidConstructor1() {
        String id = "1";
        String name = "Test Bid";
        String description = "This is a test bid";
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1);
        InetSocketAddress sellerInformations = new InetSocketAddress("127.0.0.1", 8080);
        PublicKey pubKeySignatureSeller = null; // Provide a valid PublicKey instance for actual testing

        Bid bid = new Bid(id, name, description, endDateTime, sellerInformations, pubKeySignatureSeller);

        Assertions.assertEquals(id, bid.getId());
        Assertions.assertEquals(name, bid.getName());
        Assertions.assertEquals(description, bid.getDescription());
        Assertions.assertNotNull(bid.getStartDateTime());
        Assertions.assertEquals(endDateTime, bid.getEndDateTime());
        Assertions.assertEquals(sellerInformations, bid.getSellerSocketAddress());
        Assertions.assertEquals(pubKeySignatureSeller, bid.getSellerSignaturePublicKey());
    }

    @Test
    public void testBidConstructor2() {
        String id = "1";
        String name = "Test Bid";
        String description = "This is a test bid";
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1);

        Bid bid = new Bid(id, name, description, endDateTime);

        Assertions.assertEquals(id, bid.getId());
        Assertions.assertEquals(name, bid.getName());
        Assertions.assertEquals(description, bid.getDescription());
        Assertions.assertNotNull(bid.getStartDateTime());
        Assertions.assertEquals(endDateTime, bid.getEndDateTime());
        Assertions.assertNull(bid.getSellerSocketAddress());
        Assertions.assertNull(bid.getSellerSignaturePublicKey());
    }

    @Test
    public void testStartBid() {
        String id = "1";
        String name = "Test Bid";
        String description = "This is a test bid";
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1);

        Bid bid = new Bid(id, name, description, endDateTime);
        bid.startBid();

        Assertions.assertNotNull(bid.getStartDateTime());
    }

    @Test
    public void testIsOver() {
        String id = "1";
        String name = "Test Bid";
        String description = "This is a test bid";
        LocalDateTime endDateTime = LocalDateTime.now().minusDays(1);

        Bid bid = new Bid(id, name, description, endDateTime);

        Assertions.assertTrue(bid.isOver());
    }
}
package com.projetenchere.common.models;

import com.projetenchere.common.model.Bid;
import com.projetenchere.common.model.CurrentBids;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrentBidsTests {

    @Mock
    private Bid bidMock;

    private CurrentBids currentBids;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        currentBids = new CurrentBids();
    }

    @Test
    public void testAddCurrentBid() {
        currentBids.addCurrentBid(bidMock);
        assertTrue(currentBids.getCurrentBids().contains(bidMock));
    }

    @Test
    public void testGetBid() {
        String idBid = "testId";
        when(bidMock.getId()).thenReturn(idBid);
        currentBids.addCurrentBid(bidMock);
        assertSame(bidMock, currentBids.getBid(idBid));
    }

    @Test
    public void testGetBid_BidNotFound() {
        String idBid = "testId";
        when(bidMock.getId()).thenReturn("anotherId");
        currentBids.addCurrentBid(bidMock);
        assertNull(currentBids.getBid(idBid));
    }

    @Test
    public void testStartAllBids() {
        currentBids.addCurrentBid(bidMock);
        currentBids.startAllBids();
        verify(bidMock, times(1)).startBid();
    }

    @Test
    public void testStartBids() {
        String id = "testId";
        when(bidMock.getId()).thenReturn(id);
        currentBids.addCurrentBid(bidMock);
        currentBids.startBids(id);
        verify(bidMock, times(1)).startBid();
    }

    @Test
    public void testStartBids_BidNotFound() {
        String id = "testId";
        when(bidMock.getId()).thenReturn("anotherId");
        currentBids.addCurrentBid(bidMock);
        currentBids.startBids(id);
        verify(bidMock, never()).startBid();
    }
}

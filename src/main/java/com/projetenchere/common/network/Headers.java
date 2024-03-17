package com.projetenchere.common.network;

import java.io.Serializable;

public enum Headers implements Serializable {
    GET_PUB_KEY,
    OK_PUB_KEY,
    ERROR,
    GET_CURRENT_BIDS,
    OK_CURRENT_BIDS,
    SEND_OFFER,
    CHECK_LIST,
    ABORT,
    GET_RESULTS,
    OK_RESULTS,
    SET_WIN_EXP,
    OK_WIN_EXP,
    NEW_BID,
    OK_NEW_BID,
    RESOLVE_BID,
    RESOLVE_BID_OK,
    GOODBYE_HAVE_A_NICE_DAY,
    TEST,
    OK_TEST
}

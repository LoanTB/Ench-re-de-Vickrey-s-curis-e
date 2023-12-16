package com.projetenchere.common.network;

import java.io.Serializable;

public enum Headers implements Serializable {
    GET_PUB_KEY,
    OK_PUB_KEY,
    ERROR,
    GET_CURRENT_BIDS,
    OK_CURRENT_BIDS,
    GET_WIN_STATUS,
    OK_WIN_STATUS
}

package com.projetenchere.common;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import java.io.Serializable;


public interface ICommunicator {

    void sendDataToParty(DataWrapper<? extends Serializable> data);
    void handleDataFromParty(IDataHandler handler);
}

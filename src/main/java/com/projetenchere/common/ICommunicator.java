package com.projetenchere.common;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import java.io.Serializable;


public interface ICommunicator {

    void sendDataToParty(Party party, DataWrapper<? extends Serializable> data);
    DataWrapper<? extends Serializable> waitForDataFromParty(Party party, IDataHandler handler);
}

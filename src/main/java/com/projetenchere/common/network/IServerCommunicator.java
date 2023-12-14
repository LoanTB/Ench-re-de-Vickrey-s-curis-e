package com.projetenchere.common.network;

public interface IServerCommunicator{
    public void reply(NetworkDataHeaders incoming, DataWrapper<?> dataOut);
}

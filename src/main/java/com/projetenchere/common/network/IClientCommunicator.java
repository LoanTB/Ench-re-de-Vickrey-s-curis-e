package com.projetenchere.common.network;

import java.io.Serializable;

public interface IClientCommunicator {
    public <T extends Serializable> DataWrapper<T> requestData(NetworkDataHeaders headerOut, NetworkDataHeaders headerIn);

}

package com.projetenchere.common.network;

import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.network.DataWrapper;

import java.io.Serializable;

public interface IDataHandler {
    <T extends Serializable> DataWrapper<T> handle(Serializable data);

}

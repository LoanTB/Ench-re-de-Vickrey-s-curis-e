package com.projetenchere.common.network;

import java.io.Serializable;

public interface IDataHandler {
    void handle(DataWrapper<? extends Serializable> wrapper);
}

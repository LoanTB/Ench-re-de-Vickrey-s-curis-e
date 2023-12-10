package com.projetenchere.common;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import java.io.Serializable;

public interface IDataHandler {
    void handle(DataWrapper<? extends Serializable> wrapper);
}

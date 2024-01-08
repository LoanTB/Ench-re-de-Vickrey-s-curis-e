package com.projetenchere.common.network;

import java.io.Serializable;

public interface IDataHandler {
    <T extends Serializable> DataWrapper<T> handle(Serializable data);

}

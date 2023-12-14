package com.projetenchere.common.network;

import java.io.Serializable;

public class DataWrapper<T extends Serializable> extends NetworkData {
    private final T object;

    public DataWrapper(T object, NetworkDataHeaders header) {
        super(header);
        this.object = object;
    }

    public T unwrap() {
        return this.object;
    }

}


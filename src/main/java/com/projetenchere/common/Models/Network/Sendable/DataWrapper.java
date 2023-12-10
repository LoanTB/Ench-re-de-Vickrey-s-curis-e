package com.projetenchere.common.Models.Network.Sendable;

import java.io.Serializable;

public class DataWrapper<T extends Serializable> implements Serializable {
    private final T object;

    public DataWrapper(T object) {
        this.object = object;
    }

    public T unwrap() {
        return this.object;
    }
}


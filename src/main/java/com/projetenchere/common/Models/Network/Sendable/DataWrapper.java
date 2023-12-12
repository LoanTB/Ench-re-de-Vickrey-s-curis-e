package com.projetenchere.common.Models.Network.Sendable;

import com.projetenchere.common.DataWrapperHeaders;

import java.io.Serializable;

public class DataWrapper<T extends Serializable> implements Serializable {
    private final T object;
    private DataWrapperHeaders header;

    public DataWrapper(T object) {
        this.object = object;
    }

    public boolean checkHeader(DataWrapperHeaders otherHeader) {
        return this.header.equals(otherHeader);
    }

    public T unwrap() {
        return this.object;
    }
}


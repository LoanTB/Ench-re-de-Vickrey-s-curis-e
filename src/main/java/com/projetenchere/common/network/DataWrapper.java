package com.projetenchere.common.network;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.security.Signature;

public class DataWrapper<T extends Serializable> extends NetworkData {
    private final T object;

    public DataWrapper(T object, Headers header, @Nullable Signature signature) {
        super(header, signature);
        this.object = object;
    }

    public T unwrap() {
        return this.object;
    }

}


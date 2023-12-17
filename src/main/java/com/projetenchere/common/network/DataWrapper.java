package com.projetenchere.common.network;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.security.Signature;

public class DataWrapper<T extends Serializable> {
    @Nullable
    private final T object;

    private Headers header;

    public DataWrapper(T object, Headers header) {
        this.object = object;
        this.header = header;
    }

    public DataWrapper(Headers header) {
        this(null, header);
    }

    public boolean containsWrappedObject() {
        return this.object != null;
    }
    
    public Headers getHeader() {
        return this.header;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkHeader(Headers otherHeader) {
        if (this.getHeader().equals(Headers.ERROR) || otherHeader.equals(Headers.ERROR)) {
            return false;
        }
        return this.getHeader().equals(otherHeader);
    }

    public static DataWrapper<?> error() {
        return new DataWrapper<>(Headers.ERROR, null);
    }

    public T unwrap() {
        return containsWrappedObject() ? this.object : null;
    }

}


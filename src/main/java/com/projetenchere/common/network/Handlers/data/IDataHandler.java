package com.projetenchere.common.network.Handlers.data;

import com.projetenchere.common.network.DataWrapper;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface IDataHandler {
    @Nullable
    <T extends Serializable> DataWrapper<T> handle();

    boolean generatesReply();
}

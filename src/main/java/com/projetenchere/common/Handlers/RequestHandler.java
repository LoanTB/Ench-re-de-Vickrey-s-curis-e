package com.projetenchere.common.Handlers;

import com.projetenchere.common.Models.Network.Communication.ObjectReceived;

public interface RequestHandler {
    void handle(ObjectReceived objectReceived) throws Exception;
}

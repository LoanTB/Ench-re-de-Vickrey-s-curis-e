package com.projetenchere.common.Handlers;

import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public interface RequestHandler {
    void handle(ObjectReceived objectReceived) throws Exception;
}

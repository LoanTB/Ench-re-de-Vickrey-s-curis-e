package com.projetenchere.common.Models.Network;

import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public interface RequestHandler {
    void handle(ObjectSender objectSender);
}

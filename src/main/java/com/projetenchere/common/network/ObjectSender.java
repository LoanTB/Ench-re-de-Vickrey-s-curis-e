package com.projetenchere.common.network;

import java.io.Serializable;

public class ObjectSender implements Serializable {
    final String IP_serder;
    final String PORT_sender;
    final Object object;
    final Class<?> objectClass;

    public ObjectSender(String IP_serder, String PORT_sender, Object object, Class<?> objectClass) {
        this.IP_serder = IP_serder;
        this.PORT_sender = PORT_sender;
        if (!objectClass.isInstance(object)){
            throw new IllegalArgumentException("Le type spécifié n'est pas le type de l'objet");
        }
        this.object = object;
        this.objectClass = objectClass;
    }
}

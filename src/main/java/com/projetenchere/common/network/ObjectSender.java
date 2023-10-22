package com.projetenchere.common.network;

import java.io.Serializable;

public class ObjectSender implements Serializable {
    private final String IP_serder;
    private final int PORT_sender;
    private final Object object;
    private final Class<?> objectClass;

    public ObjectSender(String IP_serder, int PORT_sender, Object object, Class<?> objectClass) {
        this.IP_serder = IP_serder;
        this.PORT_sender = PORT_sender;
        if (!objectClass.isInstance(object)){
            throw new IllegalArgumentException("Le type spécifié n'est pas le type de l'objet");
        }
        this.object = object;
        this.objectClass = objectClass;
    }

    public String getIP_sender() {
        return IP_serder;
    }

    public int getPORT_sender() {
        return PORT_sender;
    }

    public Object getObject() {
        return object;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }
}

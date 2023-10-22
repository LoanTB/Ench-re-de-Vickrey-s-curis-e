package com.projetenchere.common.Model.Serializable;

import java.io.Serializable;

public class ObjectSender implements Serializable {
    private final String IP_sender;
    private final int PORT_sender;
    private final Object object;
    private final Class<?> objectClass;

    public ObjectSender(String IP_sender, int PORT_sender, Object object, Class<?> objectClass) {
        this.IP_sender = IP_sender;
        this.PORT_sender = PORT_sender;
        if (!objectClass.isInstance(object)){
            throw new IllegalArgumentException("Le type spécifié n'est pas le type de l'objet");
        }
        this.object = object;
        this.objectClass = objectClass;
    }

    public String getIP_sender() {
        return IP_sender;
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

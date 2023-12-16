package com.projetenchere.common.Utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface I_KeyFileUtil {
    public void saveKeyPair(KeyPair keyPair);
    public boolean isKeyPairSaved();
    public PrivateKey getPrivateKeyFromFile() throws Exception;
    public PublicKey getPublicKeyFromFile() throws Exception;
}

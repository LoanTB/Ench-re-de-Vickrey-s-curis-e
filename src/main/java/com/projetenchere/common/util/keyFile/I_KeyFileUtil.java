package com.projetenchere.common.util.keyFile;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface I_KeyFileUtil {
    void generateAndSaveKeyPair();

    boolean isKeyPairSaved();

    PrivateKey getPrivateKeyFromFile() throws Exception;

    PublicKey getPublicKeyFromFile() throws Exception;
}

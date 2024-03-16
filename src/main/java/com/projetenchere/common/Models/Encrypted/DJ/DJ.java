package com.projetenchere.common.Models.Encrypted.DJ;

import java.security.Provider;

public class DJ extends Provider {
    protected DJ() {
        super("DJ", "0.1", "Implementation of The Damgård–Jurik cryptosystem");
    }
}

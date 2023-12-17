package com.projetenchere.common.Models;

import java.io.Serializable;

public record Winner(String bidId, byte[] encryptedPrice, double price) implements Serializable {

}

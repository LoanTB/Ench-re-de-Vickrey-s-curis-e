package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.SignatureUtil;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignedEncryptedOffersTest {

    @Test
    public void test_simple_SignedEncryptedOffersTest() throws Exception {

        KeyPair kpManager = EncryptionUtil.generateKeyPair();
        PublicKey pbKey = kpManager.getPublic();

        Seller a = Seller.getInstance();

        KeyPair kpOffer1 = EncryptionUtil.generateKeyPair();
        Signature mySignature1 = SignatureUtil.initSignatureForSigning(kpOffer1.getPrivate());
        Offer myOffer1 = new Offer("1", "18");
        EncryptedOffer encryptedOffer1 = new EncryptedOffer(mySignature1, myOffer1, kpOffer1.getPublic(), kpManager.getPublic(), "1");


        KeyPair kpOffer2 = EncryptionUtil.generateKeyPair();
        Signature mySignature2 = SignatureUtil.initSignatureForSigning(kpOffer2.getPrivate());
        Offer myOffer2 = new Offer("1", "19");
        EncryptedOffer encryptedOffer2 = new EncryptedOffer(mySignature2, myOffer2, kpOffer2.getPublic(), kpManager.getPublic(), "1");

        Set<EncryptedOffer> offers = new HashSet<>();
        offers.add(encryptedOffer1);
        offers.add(encryptedOffer2);

        EncryptedOffersSet setEncrypted = new EncryptedOffersSet("1", offers);
        a.setEncryptedOffers(setEncrypted);


        KeyPair kpOffer3 = EncryptionUtil.generateKeyPair();
        Signature mySignature3 = SignatureUtil.initSignatureForSigning(kpOffer3.getPrivate());
        a.setSignature(mySignature3);

        SignedEncryptedOfferSet setSigned = new SignedEncryptedOfferSet(mySignature3, kpOffer3.getPublic(), setEncrypted);

        a.setEncryptedOffersSignedBySeller(setSigned);
        a.reSignedEncryptedOffers();

        for(EncryptedOffer b : a.getEncryptedOffersSignedBySeller().getSet().getOffers()){

            assertTrue(SignatureUtil.verifyDataSignature(a,));
        }

        EncryptedOffersSet offersToVerify = setSigned.getSet();
        boolean verifyDataSignature = SignatureUtil.verifyDataSignature(offersToVerify, setSigned.getSetSigned(), kpOffer3.getPublic());

        assertTrue(verifyDataSignature);

        Set<EncryptedOffer> offersSetToVerify = offersToVerify.getOffers();
        for (EncryptedOffer o : offersSetToVerify) {
            assertTrue(SignatureUtil.verifyDataSignature(o.getPrice(), o.getPriceSigned(), o.getSignaturePublicKey()));
        }


    }
}





package com.projetenchere.Manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ManagerAppTest {
/*
    @Test
    public void test_if_main_method_was_implemented() {
        assertDoesNotThrow(() -> ManagerApp.main(new String[1]));
    }

    @Test
    public void test_decryptEncryptedPrice_differentKeys_sameValue() {
        ManagerController controller = new ManagerController();
        controller.generateManagerKeys();
        double value = 2.1;
        byte[] valueEnc = EncryptionUtil.encryptPrice(value, controller.manager.getManagerPublicKey());

        controller.generateManagerKeys();
        double value2 = 2.1;
        byte[] valueEnc2 = EncryptionUtil.encryptPrice(value, controller.manager.getManagerPublicKey());

        assert valueEnc != valueEnc2 : "Les chiffrés doivnet être différents";
        Systeout.println("Chiffrés sont différents.");
    }


    @Test
    public void test_showprices() throws Exception {
        Set<Double> prices = new HashSet<>();
        prices.add(5.1);
        prices.add(6.8);
        prices.add(39.3);
        prices.add(25.2);
        ManagerController controller = new ManagerController();
        controller.showPrices(prices);
    }

    @Test
    public void test_getwinnerprice_withOneBidder() throws Exception {
        ManagerController controller = new ManagerController();
        controller.generateManagerKeys();
        PublicKey key = controller.manager.getManagerPublicKey();
        Set<Double> prices = new HashSet<>();
        prices.add(5.1);

        Winner win = controller.getWinnerPrice(key, prices);

        Double check = EncryptionUtil.decryptPrice(win.getEncryptedId(), controller.manager.getManagerPrivateKey());

        assert check == win.getPrice() : "Les prix doivent être pareil";
        System.out.println("Prix chiffré de winner et le prix à payer de winner sont différents.");
    }

    @Test
    public void test_getwinnerprice_withTwoBidder() throws Exception {
        ManagerController controller = new ManagerController();
        controller.generateManagerKeys();
        PublicKey key = controller.manager.getManagerPublicKey();
        PrivateKey pince = controller.manager.getManagerPrivateKey();

        Set<Double> prices = new HashSet<>();
        prices.add(5.1);
        prices.add(6.8);
        prices.add(1.8);

        byte[] maxEnc = EncryptionUtil.encryptPrice(6.8, key);
        Winner win = controller.getWinnerPrice(key, prices);

        Double ech = EncryptionUtil.decryptPrice(maxEnc, pince);
        Double pec = EncryptionUtil.decryptPrice(win.getEncryptedId(), pince);

        assert ech.equals(pec) : "Même prix";
        System.out.println("Même prix");

    }

    @Test
    void main() {
    }
    */
}

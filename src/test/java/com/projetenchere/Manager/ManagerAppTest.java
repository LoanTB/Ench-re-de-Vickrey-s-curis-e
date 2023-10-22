package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.util.HashSet;
import com.projetenchere.common.Model.Winner;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ManagerAppTest {
    @Test
    public void test_if_main_method_was_implemented() {
        assertDoesNotThrow(() -> ManagerApp.main(new String[1]));
    }

    @Test
    public void test_showprices() throws Exception{
        Set<Double> prices = new HashSet<>();
        prices.add(5.1);
        prices.add(6.8);
        prices.add(39.3);
        prices.add(25.2);
        ManagerController controller = new ManagerController();
        controller.showPrices(prices);
    }

    @Test
    public void test_getwinnerprice_withOneBidder() throws Exception{
        ManagerController controller = new ManagerController();
        controller.generateManagerKeys();
        PublicKey key = controller.manager.getManagerPublicKey();
        Set<Double> prices = new HashSet<>();
        prices.add(5.1);
        //prices.add(6.8);
        //prices.add(39.3);
        //prices.add(25.2);

        Winner win = controller.getWinnerPrice(key,prices);
        controller.showWinnerPrice(win);

    }

    @Test
    void main() {
    }
}

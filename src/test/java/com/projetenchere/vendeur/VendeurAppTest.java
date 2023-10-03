package com.projetenchere.vendeur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VendeurAppTest {
    @Test
    public void test_if_main_method_was_implemented() {
        assertDoesNotThrow(() -> VendeurApp.main(new String[1]));
    }
}

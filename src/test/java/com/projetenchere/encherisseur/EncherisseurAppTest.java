package com.projetenchere.encherisseur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EncherisseurAppTest {
    @Test
    public void test_if_main_method_was_implemented() {
        assertDoesNotThrow(() -> EncherisseurApp.main(new String[1]));
    }
}

package com.projetenchere.gestionnaire;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GestionnaireAppTest {
    @Test
    public void test_if_main_method_was_implemented() {
        assertDoesNotThrow(() -> GestionnaireApp.main(new String[1]));
    }
}

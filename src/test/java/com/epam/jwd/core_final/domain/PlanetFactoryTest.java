package com.epam.jwd.core_final.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlanetFactoryTest {
    @Test
    void createTest() {
        EntityFactory<Planet> factory = new PlanetFactory();
        assertDoesNotThrow(() -> {
            factory.create("test", 4, 4);
        });
        assertThrows(ClassCastException.class, () -> {
            factory.create("test", "fdfd", 4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create("test", 2, 3, 4);
        });
    }
}
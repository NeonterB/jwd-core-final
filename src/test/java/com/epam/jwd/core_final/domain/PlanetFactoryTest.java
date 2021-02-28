package com.epam.jwd.core_final.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
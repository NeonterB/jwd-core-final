package com.epam.jwd.core_final.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipFactoryTest {

    private static Stream<Arguments> createTest2() {
        return Stream.of(
                //Arguments.of((Object) new Object[]{"test", 5L, new HashMap<Role, String>()}),
                Arguments.of((Object) new Object[]{"test", "fd", new HashMap<Role, Short>()})
        );
    }

    @Test
    void createTest1() {
        EntityFactory<Spaceship> factory = new SpaceshipFactory();
        assertDoesNotThrow(() -> {
            factory.create("test", 5L, new HashMap<Role, Short>());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(1, 2, 3, 4);
        });
    }

    @ParameterizedTest
    @MethodSource
    void createTest2(Object... args) {
        EntityFactory<Spaceship> factory = new SpaceshipFactory();
        assertThrows(ClassCastException.class, () -> {
            factory.create(args);
        });
    }
}
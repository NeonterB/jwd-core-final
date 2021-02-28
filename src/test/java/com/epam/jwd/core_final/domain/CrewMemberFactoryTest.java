package com.epam.jwd.core_final.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CrewMemberFactoryTest {
    @Test
    void createTest() {
        EntityFactory<CrewMember> factory = new CrewMemberFactory();
        assertDoesNotThrow(() -> {
            factory.create(Role.COMMANDER, "test", Rank.FIRST_OFFICER);
        });
        assertThrows(ClassCastException.class, () -> {
            factory.create(Role.COMMANDER, "Test", "fdfd");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(1, 2, 3, 4);
        });
    }
}
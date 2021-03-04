package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.io.EraseStrategy;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipEraserTest {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipEraserTest.class);
    private static final EraseStrategy<Spaceship> eraser =  new SpaceshipEraser();
    private static final SpaceshipFactory factory = new SpaceshipFactory();

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void erase() {
        try {
            int lengthBefore = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();

            Map<Role, Short> crew = new TreeMap<>();
            crew.put(Role.MISSION_SPECIALIST, (short) 7);
            crew.put(Role.FLIGHT_ENGINEER, (short) 7);
            crew.put(Role.PILOT, (short) 2);
            crew.put(Role.COMMANDER, (short) 1);
            eraser.erase(factory.create("sparta", 754471L, crew));

            int lengthAfter = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();

            assertEquals(lengthBefore - 1, lengthAfter);
            assertThrows(EntityNotFoundException.class, () ->{
                eraser.erase(factory.create("spartaa", 754571L, crew));
            });
            assertThrows(EntityNotFoundException.class, () ->{
                eraser.erase(factory.create("sparta", 754572L, crew));
            });
        } catch (IOException | EntityNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
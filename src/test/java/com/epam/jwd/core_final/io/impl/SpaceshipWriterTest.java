package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.io.ReadStrategy;
import com.epam.jwd.core_final.io.WriteStrategy;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipWriterTest {
    private static final ReadStrategy<Spaceship> reader = new SpaceshipReader();
    private static final WriteStrategy<Spaceship> writer = new SpaceshipWriter();
    private static final Logger logger = LoggerFactory.getLogger(SpaceshipWriterTest.class);

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void readEntities() {
        try {
            int sizeBefore = reader.readEntities().size();

            Map<Role, Short> crew = new TreeMap<>();
            crew.put(Role.MISSION_SPECIALIST, (short) 7);
            crew.put(Role.FLIGHT_ENGINEER, (short) 7);
            crew.put(Role.PILOT, (short) 2);
            crew.put(Role.COMMANDER, (short) 1);
            writer.writeEntity(new SpaceshipFactory().create("test", 1L, crew));

            int sizeAfter = reader.readEntities().size();
            assertEquals(sizeAfter, sizeBefore + 1);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
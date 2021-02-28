package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipReaderTest {
    @Test
    void readEntitiesTest(){
        PropertyReaderUtil.loadProperties();
        assertThrows(IOException.class, () -> {
            new SpaceshipReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getSpaceShipFileDir())
            );
        });
    }
}
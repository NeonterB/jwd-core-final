package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceMapReaderTest {
    @Test
    void readEntitiesTest(){
        PropertyReaderUtil.loadProperties();
        assertDoesNotThrow(() -> {
            new SpaceMapReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getSpaceMapFileDir())
            );
        });
    }
}
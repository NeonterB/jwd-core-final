package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrewReaderTest {
    @Test
    void readEntitiesTest(){
        PropertyReaderUtil.loadProperties();
        assertThrows(Exception.class, () -> {
            new CrewReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getCrewFileDir())
            );
        });
    }
}
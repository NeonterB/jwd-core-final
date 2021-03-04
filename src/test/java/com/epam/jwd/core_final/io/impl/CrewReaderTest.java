package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.io.ReadStrategy;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CrewReaderTest {
    private static final ReadStrategy<CrewMember> reader = new CrewReader();
    private static final Logger logger = LoggerFactory.getLogger(CrewReaderTest.class);

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void readEntities() {
        try {
            assertEquals(5, reader.readEntities().size());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
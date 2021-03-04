package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SpaceMapReaderTest {

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void readEntities() throws IOException {
        Assertions.assertEquals(34, new SpaceMapReader().readEntities().size());
    }
}
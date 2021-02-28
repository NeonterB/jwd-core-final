package com.epam.jwd.core_final.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderUtilTest {
    @Test
    void loadPropertiesTest(){
        PropertyReaderUtil.loadProperties();
        System.out.println(PropertyReaderUtilTest.class.getResource("application.properties"));
        assertTrue(true);
    }
}
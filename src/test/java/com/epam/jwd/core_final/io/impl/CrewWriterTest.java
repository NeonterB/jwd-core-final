package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.CrewMemberFactory;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.io.ReadStrategy;
import com.epam.jwd.core_final.io.WriteStrategy;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrewWriterTest {

    private static final ReadStrategy<CrewMember> reader = new CrewReader();
    private static final WriteStrategy<CrewMember> writer = new CrewWriter();
    private static final Logger logger = LoggerFactory.getLogger(CrewWriterTest.class);

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void readEntities() {
        try {
            int sizeBefore = reader.readEntities().size();
            writer.writeEntity(new CrewMemberFactory().create(Role.MISSION_SPECIALIST, "test", Rank.SECOND_OFFICER));
            int sizeAfter = reader.readEntities().size();
            assertEquals(sizeAfter, sizeBefore + 1);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
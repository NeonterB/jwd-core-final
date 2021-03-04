package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.CrewMemberFactory;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
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

import static org.junit.jupiter.api.Assertions.*;

class CrewEraserTest {

    private static final Logger logger = LoggerFactory.getLogger(CrewEraserTest.class);
    private static final EraseStrategy<CrewMember> eraser =  new CrewEraser();
    private static final CrewMemberFactory factory = new CrewMemberFactory();

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void erase() {
        try {
            int lengthBefore = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            eraser.erase(factory.create(Role.MISSION_SPECIALIST, "zoe Day", Rank.SECOND_OFFICER));
            int lengthAfter = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            assertEquals(lengthBefore - 1, lengthAfter);
            assertThrows(EntityNotFoundException.class, () ->{
                eraser.erase(factory.create(Role.MISSION_SPECIALIST, "Zoee Day", Rank.SECOND_OFFICER));
            });
        } catch (IOException | EntityNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
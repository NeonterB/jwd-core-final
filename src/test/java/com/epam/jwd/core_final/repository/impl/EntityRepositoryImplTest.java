package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityRepositoryImplTest {
    private static final Logger logger = LoggerFactory.getLogger(EntityRepositoryImplTest.class);

    @BeforeAll
    static void setUp(){
        PropertyReaderUtil.loadProperties();
    }

    @Test
    void getAll() {
        try {
            assertEquals(5, EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size());
            assertEquals(30, EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size());
            assertEquals(34, EntityRepositoryImpl.getInstance().getAll(Planet.class).size());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void create() {
        try {
            int sizeBefore = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            EntityRepositoryImpl.getInstance().create(new CrewMemberFactory().create(Role.MISSION_SPECIALIST, "test", Rank.SECOND_OFFICER));
            int sizeAfter = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            assertEquals(sizeAfter, sizeBefore + 1);

            sizeBefore = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();
            Map<Role, Short> crew = new TreeMap<>();
            crew.put(Role.MISSION_SPECIALIST, (short) 7);
            crew.put(Role.FLIGHT_ENGINEER, (short) 7);
            crew.put(Role.PILOT, (short) 2);
            crew.put(Role.COMMANDER, (short) 1);
            EntityRepositoryImpl.getInstance().create(new SpaceshipFactory().create("test", 1L, crew));
            sizeAfter = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();
            assertEquals(sizeAfter, sizeBefore + 1);

        } catch (IOException | EntityExistsException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            int lengthBefore = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            EntityRepositoryImpl.getInstance().delete(new CrewMemberFactory().create(Role.MISSION_SPECIALIST, "zoe Day", Rank.SECOND_OFFICER));
            int lengthAfter = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).size();
            assertEquals(lengthBefore - 1, lengthAfter);
            assertThrows(EntityNotFoundException.class, () ->{
                EntityRepositoryImpl.getInstance().delete(new CrewMemberFactory().create(Role.MISSION_SPECIALIST, "Zoee Day", Rank.SECOND_OFFICER));
            });



            lengthBefore = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();

            Map<Role, Short> crew = new TreeMap<>();
            crew.put(Role.MISSION_SPECIALIST, (short) 7);
            crew.put(Role.FLIGHT_ENGINEER, (short) 7);
            crew.put(Role.PILOT, (short) 2);
            crew.put(Role.COMMANDER, (short) 1);
            EntityRepositoryImpl.getInstance().delete(new SpaceshipFactory().create("sparta", 754471L, crew));

            lengthAfter = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).size();

            assertEquals(lengthBefore - 1, lengthAfter);
            assertThrows(EntityNotFoundException.class, () ->{
                EntityRepositoryImpl.getInstance().delete(new SpaceshipFactory().create("spartaa", 754571L, crew));
            });
            assertThrows(EntityNotFoundException.class, () ->{
                EntityRepositoryImpl.getInstance().delete(new SpaceshipFactory().create("sparta", 754572L, crew));
            });
        } catch (IOException | EntityNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
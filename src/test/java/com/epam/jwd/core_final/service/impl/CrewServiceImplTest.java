package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrewServiceImplTest {
    private static ApplicationContext context;

    @BeforeAll
    static void setUp() {
        Main.main();
        context = Main.getApplicationMenu().getApplicationContext();
    }

    @Test
    void findAllCrewMembers() {
        assertEquals(5, CrewServiceImpl.getInstance().findAllCrewMembers().size());
    }

    @Test
    void findAllCrewMembersByCriteria() {
        try {
            CrewMemberCriteria criteria = (CrewMemberCriteria) CrewMemberCriteria.newBuilder()
                    .setRank(Rank.SECOND_OFFICER)
                    .build();

            assertEquals(3, CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria).size());


            criteria = (CrewMemberCriteria) CrewMemberCriteria.newBuilder()
                    .setRank(Rank.SECOND_OFFICER)
                    .setRole(Role.PILOT)
                    .build();
            assertEquals(2, CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria).size());

            criteria = (CrewMemberCriteria) CrewMemberCriteria.newBuilder()
                    .setRank(Rank.SECOND_OFFICER)
                    .setRole(Role.PILOT)
                    .setName("test")
                    .build();
            CrewMemberCriteria finalCriteria = criteria;
            assertThrows(EntityNotFoundException.class, () -> CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(finalCriteria));
        } catch (EntityNotFoundException e) {
            //
        }
    }

    @Test
    void findCrewMemberByCriteria() {
        CrewMemberCriteria criteria = (CrewMemberCriteria) CrewMemberCriteria.newBuilder()
                .setRank(Rank.SECOND_OFFICER)
                .build();

        CrewMemberCriteria finalCriteria1 = criteria;
        assertDoesNotThrow(() -> {
                    CrewServiceImpl.getInstance().findCrewMemberByCriteria(finalCriteria1)
                            .orElseThrow(() -> new EntityNotFoundException(""));
                }
        );

        criteria = (CrewMemberCriteria) CrewMemberCriteria.newBuilder()
                .setRank(Rank.SECOND_OFFICER)
                .setRole(Role.PILOT)
                .setName("test")
                .build();
        CrewMemberCriteria finalCriteria2 = criteria;
        assertThrows(EntityNotFoundException.class, () -> CrewServiceImpl.getInstance().findCrewMemberByCriteria(finalCriteria2)
                .orElseThrow(() -> new EntityNotFoundException("")));
    }

    @Test
    void createCrewMember() {
    }

    @Test
    void deleteCrewMember() {

    }
}
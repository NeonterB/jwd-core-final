package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.EntityOccupiedException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class FlightMissionTest {
    private static final Logger logger = LoggerFactory.getLogger(FlightMissionTest.class);

    @BeforeAll
    static void setUp() {
        Main.main();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRun() throws EntityExistsException, EntityNotFoundException, EntityOccupiedException, InterruptedException, IOException {
        Collection<CrewMember> crew = CrewServiceImpl.getInstance().findAllCrewMembers();
        Map<Role, Short> spaceshipCrew = new TreeMap<>();
        spaceshipCrew.put(Role.COMMANDER, (short) 2);
        spaceshipCrew.put(Role.FLIGHT_ENGINEER, (short) 2);
        spaceshipCrew.put(Role.MISSION_SPECIALIST, (short) 1);
        Spaceship spaceship = SpaceshipServiceImpl.getInstance().createSpaceship(
                new SpaceshipFactory().create("testShip", 100000L, spaceshipCrew)
        );
        Planet from = new PlanetFactory().create("Earth", 0, 0);
        Planet to =  new PlanetFactory().create("Mars", 10, 10);
        FlightMission mission = MissionServiceImpl.getInstance().createMission(
                new FlightMissionFactory().create("testMission", LocalDateTime.now().plusSeconds(15), from, to)
        );
        MissionServiceImpl.getInstance().assignSpaceshipOnMission(mission, spaceship);
        for (CrewMember crewMember : crew) {
            MissionServiceImpl.getInstance().assignCrewMemberOnMission(mission, crewMember);
        }
        boolean canEnd = false;
        Collection<FlightMission> missions = (Collection<FlightMission>) Main.getApplicationMenu().getApplicationContext()
                .retrieveBaseEntityList(FlightMission.class);
        while (!canEnd) {
            long planned = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.PLANNED)).count();
            long ready = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.READY)).count();
            long inProgress = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.IN_PROGRESS)).count();
            long failed = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.FAILED)).count();
            long completed = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.COMPLETED)).count();
            long cancelled = missions.stream().filter(m -> m.getMissionStatus().equals(MissionStatus.CANCELLED)).count();
            logger.debug("\n");
            logger.debug(planned + " missions planned");
            logger.debug(ready + " missions ready");
            logger.debug(inProgress + " missions inProgress");
            logger.debug(failed + " missions failed");
            logger.debug(completed + " missions completed");
            logger.debug(cancelled + " missions cancelled");
            Thread.sleep(10000);
            if (planned + ready + inProgress == 0) canEnd = true;
        }
    }
}
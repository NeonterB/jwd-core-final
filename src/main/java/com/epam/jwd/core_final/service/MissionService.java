package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.EntityOccupiedException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    Collection<FlightMission> findAllMissions() throws EntityNotFoundException;

    Collection<FlightMission> findAllMissionsByCriteria(Criteria<FlightMission> criteria) throws EntityNotFoundException;

    Optional<FlightMission> findMissionByCriteria(Criteria<FlightMission> criteria) throws EntityNotFoundException;

    // todo create custom exception for case, when crewMember is not able to be assigned
    void assignCrewMemberOnMission(FlightMission mission, CrewMember crewMember) throws EntityOccupiedException;

    // todo create custom exception for case, when spaceship is not able to be assigned
    void assignSpaceshipOnMission(FlightMission mission, Spaceship crewMember) throws EntityOccupiedException;

    FlightMission createMission(FlightMission flightMission);

    void cancelMission(FlightMission flightMission);
}

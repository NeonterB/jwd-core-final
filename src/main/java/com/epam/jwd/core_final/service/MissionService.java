package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.MethodInfo;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.EntityOccupiedException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    @MethodInfo(menuPosition = 1, description = "Find all missions")
    Collection<FlightMission> findAllMissions() throws EntityNotFoundException;

    @MethodInfo(menuPosition = 2, description = "Find all missions by criteria")
    Collection<FlightMission> findAllMissionsByCriteria(Criteria<FlightMission> criteria);

    @MethodInfo(menuPosition = 3, description = "Find mission by criteria")
    Optional<FlightMission> findMissionByCriteria(Criteria<FlightMission> criteria);

    @MethodInfo(menuPosition = 4, description = "Assign crew member on a mission")
    void assignCrewMemberOnMission(FlightMission mission, CrewMember crewMember) throws EntityOccupiedException;

    @MethodInfo(menuPosition = 5, description = "Assign spaceship on a mission")
    void assignSpaceshipOnMission(FlightMission mission, Spaceship crewMember) throws EntityOccupiedException;

    @MethodInfo(menuPosition = 6, description = "Create new mission")
    FlightMission createMission(FlightMission flightMission) throws EntityExistsException;
}

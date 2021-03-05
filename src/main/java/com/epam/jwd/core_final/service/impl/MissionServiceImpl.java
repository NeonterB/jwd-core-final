package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.EntityOccupiedException;
import com.epam.jwd.core_final.service.MissionService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MissionServiceImpl implements MissionService {
    private static final MissionServiceImpl instance = new MissionServiceImpl();

    private MissionServiceImpl(){}

    public static MissionServiceImpl getInstance(){
        return instance;
    }

    @Override
    public Collection<FlightMission> findAllMissions() throws EntityNotFoundException {
        Collection<FlightMission> missions = (Collection<FlightMission>) Main.getApplicationMenu().getApplicationContext()
                .retrieveBaseEntityList(FlightMission.class);
        if (missions.isEmpty()) throw new EntityNotFoundException("Missions cache is empty");
        return missions;
    }

    @Override
    public Collection<FlightMission> findAllMissionsByCriteria(Criteria<FlightMission> criteria) throws EntityNotFoundException {
        ApplicationContext context = Main.getApplicationMenu().getApplicationContext();
        Collection<FlightMission> foundMissions = ((Collection<FlightMission>) context.retrieveBaseEntityList(FlightMission.class)).stream()
                .filter(criteria::meetsCriteria).collect(Collectors.toList());
        if (foundMissions.isEmpty())
            throw new EntityNotFoundException("Missions with criteria " + criteria + " not found");
        return foundMissions;
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<FlightMission> criteria) throws EntityNotFoundException {
        return findAllMissionsByCriteria(criteria).stream().findAny();
    }

    @Override
    public void assignCrewMemberOnMission(FlightMission mission, CrewMember crewMember) throws EntityOccupiedException {
        if (mission.getMissionStatus().equals(MissionStatus.READY))
            throw new EntityOccupiedException("Mission is filled");
        if (!crewMember.isReadyForNextMission())
            throw new EntityOccupiedException("CrewMember", crewMember + " is busy");
        if (mission.getAssignedSpaceship() == null) throw new NullPointerException("Assign spaceship first");
        //Crew with the same role
        List<CrewMember> missionCrew = mission.getAssignedCrew().stream()
                .filter(member -> member.getRole().equals(crewMember.getRole()))
                .collect(Collectors.toList());
        Map<Role, Short> spaceshipCrew = mission.getAssignedSpaceship().getCrew();
        if (spaceshipCrew.get(crewMember.getRole()) != null &&
                missionCrew.size() < spaceshipCrew.get(crewMember.getRole())) {
            mission.getAssignedCrew().add(crewMember);
            crewMember.setReadyForNextMission(false);
        } else
            throw new IllegalArgumentException("Can't fit " + crewMember + " into the " + mission.getAssignedSpaceship());
        short maxCrewAmount = spaceshipCrew.values().stream()
                .reduce((short) 0, (subtotal, element) -> (short) (subtotal + element));
        if (maxCrewAmount == (short) mission.getAssignedCrew().size()){
            mission.setMissionStatus(MissionStatus.READY);
        }
    }

    @Override
    public void assignSpaceshipOnMission(FlightMission mission, Spaceship spaceship) throws EntityOccupiedException {
        if (!spaceship.isReadyForNextMission()) throw new EntityOccupiedException("Spaceship", spaceship + "is busy");
        if (spaceship.getFlightDistance() < mission.getDistance()) throw new IllegalArgumentException(spaceship + "can't fly that far");
        mission.setAssignedSpaceship(spaceship);
        spaceship.setReadyForNextMission(false);
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        Collection<FlightMission> missions = (Collection<FlightMission>) Main.getApplicationMenu().getApplicationContext()
                .retrieveBaseEntityList(FlightMission.class);
        missions.add(flightMission);
        return flightMission;
    }

    @Override
    public void cancelMission(FlightMission flightMission) {

    }
}

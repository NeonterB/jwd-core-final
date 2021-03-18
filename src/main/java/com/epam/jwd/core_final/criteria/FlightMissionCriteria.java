package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceship;
    private List<CrewMember> assignedCrew;
    private MissionStatus missionStatus;
    private Planet from;
    private Planet to;

    private FlightMissionCriteria() {
    }

    @Override
    public boolean meetsCriteria(FlightMission mission) {
        return super.meetsCriteria(mission) &&
                (startDate == null || mission.getStartDate().equals(startDate)) &&
                (endDate == null || mission.getEndDate().equals(endDate)) &&
                (distance == null || mission.getDistance().equals(distance)) &&
                (assignedCrew == null || mission.getAssignedCrew().containsAll(assignedCrew)) &&
                (assignedSpaceship == null || mission.getAssignedSpaceship().equals(assignedSpaceship)) &&
                (missionStatus == null || mission.getMissionStatus().equals(missionStatus)) &&
                (from == null || mission.getFrom().equals(from)) &&
                (to == null || mission.getTo().equals(to));
    }

    public static FlightMissionCriteriaBuilder newBuilder() {
        return new FlightMissionCriteria().new FlightMissionCriteriaBuilder();
    }

    public static FlightMissionCriteria parseCriteria(String line) throws IllegalArgumentException, EntityNotFoundException {
        FlightMissionCriteriaBuilder criteriaBuilder = newBuilder();
        ApplicationProperties properties = ApplicationProperties.getInstance();
        String[] args = line.split(";");
        for (String s : args) {
            String[] pair = s.split("=");
            if (pair[0].equalsIgnoreCase("startDate")) {
                criteriaBuilder = criteriaBuilder.setStartDate(
                        LocalDateTime.parse(pair[1], DateTimeFormatter.ofPattern(properties.getDateTimeFormat()))
                );
            } else if (pair[0].equalsIgnoreCase("endDate")) {
                criteriaBuilder = criteriaBuilder.setEndDate(
                        LocalDateTime.parse(pair[1], DateTimeFormatter.ofPattern(properties.getDateTimeFormat()))
                );
            } else if (pair[0].equalsIgnoreCase("distance")) {
                criteriaBuilder = criteriaBuilder.setDistance(Long.parseLong(pair[1]));
            } else if (pair[0].equalsIgnoreCase("spaceship")) {
                Long shipId = Long.valueOf(pair[1]);
                criteriaBuilder = criteriaBuilder.setAssignedSpaceship(
                        SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(
                                SpaceshipCriteria.newBuilder().setId(shipId).build()
                        ).orElseThrow(() -> new EntityNotFoundException("Spaceship", shipId))
                );
            } else if (pair[0].equalsIgnoreCase("crew")) {
                String[] crewIdList = pair[1].split(",");
                List<CrewMember> crew = Arrays.stream(crewIdList)
                        .map(idStr -> {
                                    Long memberId = Long.parseLong(idStr);
                                    return CrewServiceImpl.getInstance().findCrewMemberByCriteria(
                                            CrewMemberCriteria.newBuilder().setId(memberId).build()
                                    ).orElse(null);
                                }
                        ).collect(Collectors.toList());
                if (crew.contains(null)) throw new UnknownEntityException("Crew member");
                criteriaBuilder = criteriaBuilder.setAssignedCrew(crew);
            } else if (pair[0].equalsIgnoreCase("status")) {
                criteriaBuilder = criteriaBuilder.setMissionStatus(MissionStatus.valueOf(pair[1].toUpperCase()));
            } else if (pair[0].equalsIgnoreCase("from")) {
                Long planetId = Long.parseLong(pair[1]);
                criteriaBuilder = criteriaBuilder.setFrom(
                        SpacemapServiceImpl.getInstance()
                                .findPlanetById(planetId)
                                .orElseThrow(() -> new EntityNotFoundException("Planet", planetId))
                );
            } else if (pair[0].equalsIgnoreCase("to")) {
                Long planetId = Long.parseLong(pair[1]);
                criteriaBuilder = criteriaBuilder.setTo(
                        SpacemapServiceImpl.getInstance()
                                .findPlanetById(planetId)
                                .orElseThrow(() -> new EntityNotFoundException("Planet", planetId))
                );
            } else if (pair[0].equalsIgnoreCase("name")) {
                criteriaBuilder = (FlightMissionCriteriaBuilder) criteriaBuilder.setName(pair[1]);
            } else if (pair[0].equalsIgnoreCase("id")) {
                criteriaBuilder = (FlightMissionCriteriaBuilder) criteriaBuilder.setId(Long.parseLong(pair[1]));
            } else{
                throw new IllegalArgumentException("Unknown field - " + pair[0]);
            }
        }
        return criteriaBuilder.build();
    }

    @Override
    public String toString() {
        return "FlightMissionCriteria{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                ", assignedSpaceship=" + assignedSpaceship +
                ", assignedCrew=" + assignedCrew +
                ", missionResult=" + missionStatus +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public class FlightMissionCriteriaBuilder extends Criteria<FlightMission>.Builder {

        private FlightMissionCriteriaBuilder() {
        }

        public FlightMissionCriteriaBuilder setStartDate(LocalDateTime startDate) {
            FlightMissionCriteria.this.startDate = startDate;
            return this;
        }

        public FlightMissionCriteriaBuilder setEndDate(LocalDateTime endDate) {
            FlightMissionCriteria.this.endDate = endDate;
            return this;
        }

        public FlightMissionCriteriaBuilder setDistance(Long distance) {
            FlightMissionCriteria.this.distance = distance;
            return this;
        }

        public FlightMissionCriteriaBuilder setAssignedCrew(List<CrewMember> crew) {
            FlightMissionCriteria.this.assignedCrew = crew;
            return this;
        }

        public FlightMissionCriteriaBuilder setAssignedSpaceship(Spaceship assignedSpaceship) {
            FlightMissionCriteria.this.assignedSpaceship = assignedSpaceship;
            return this;
        }

        public FlightMissionCriteriaBuilder setMissionStatus(MissionStatus missionStatus){
            FlightMissionCriteria.this.missionStatus = missionStatus;
            return this;
        }

        public FlightMissionCriteriaBuilder setFrom(Planet from){
            FlightMissionCriteria.this.from = from;
            return this;
        }

        public FlightMissionCriteriaBuilder setTo(Planet to){
            FlightMissionCriteria.this.to = to;
            return this;
        }

        @Override
        public FlightMissionCriteria build() {
            FlightMissionCriteria criteria = new FlightMissionCriteria();
            criteria.name = FlightMissionCriteria.this.name;
            criteria.id = FlightMissionCriteria.this.id;
            criteria.startDate = FlightMissionCriteria.this.startDate;
            criteria.endDate = FlightMissionCriteria.this.endDate;
            criteria.distance = FlightMissionCriteria.this.distance;
            criteria.assignedSpaceship = FlightMissionCriteria.this.assignedSpaceship;
            criteria.assignedCrew = FlightMissionCriteria.this.assignedCrew;
            criteria.missionStatus = FlightMissionCriteria.this.missionStatus;
            criteria.from = FlightMissionCriteria.this.from;
            criteria.to = FlightMissionCriteria.this.to;
            return criteria;
        }
    }
}

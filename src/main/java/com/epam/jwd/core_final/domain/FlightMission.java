package com.epam.jwd.core_final.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceship {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship assignedSpaceship;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;
    private Planet from;
    private Planet to;

    FlightMission(String name, LocalDate startDate, Long distance, Spaceship assignedSpaceship, Planet from, Planet to) {
        super(name);
        this.startDate = startDate;
        this.distance = distance;
        this.assignedSpaceship = assignedSpaceship;
        this.from = from;
        this.to = to;
    }
    //todo
}

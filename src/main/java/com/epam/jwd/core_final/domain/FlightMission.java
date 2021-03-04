package com.epam.jwd.core_final.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceship;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;
    private Planet from;
    private Planet to;

    FlightMission(@NotNull String name, LocalDateTime startDate, Planet from, Planet to) {
        super(name);
        this.startDate = startDate;
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceship() {
        return assignedSpaceship;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlightMission that = (FlightMission) o;
        return startDate.equals(that.startDate) && endDate.equals(that.endDate) && distance.equals(that.distance) && assignedSpaceship.equals(that.assignedSpaceship) && assignedCrew.equals(that.assignedCrew) && missionResult == that.missionResult && from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate, distance, assignedSpaceship, assignedCrew, missionResult, from, to);
    }
}

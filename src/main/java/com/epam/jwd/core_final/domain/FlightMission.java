package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceship {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionStatus}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(FlightMission.class);

    private final Long distance;
    private final Planet from;
    private final Planet to;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Spaceship assignedSpaceship;
    private List<CrewMember> assignedCrew = new ArrayList<>();
    private MissionStatus missionStatus;

    FlightMission(@NotNull String name, LocalDateTime startDate, Planet from, Planet to) {
        super(name);
        this.startDate = startDate;
        this.from = from;
        this.to = to;
        this.distance = SpacemapServiceImpl.getInstance().getDistanceBetweenPlanets(from, to);
        this.endDate = startDate.plusSeconds(distance);
        this.missionStatus = MissionStatus.PLANNED;
        new Timer().schedule(new FlightMissionExecutor(), Date
                .from(startDate.atZone(ZoneId.systemDefault())
                        .toInstant()));
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
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

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    public MissionStatus getMissionStatus() {
        return missionStatus;
    }

    public void setAssignedSpaceship(Spaceship assignedSpaceship) {
        this.assignedSpaceship = assignedSpaceship;
    }

    public void setMissionStatus(MissionStatus missionStatus) {
        this.missionStatus = missionStatus;
    }

    @Override
    public void run() {
        if (missionStatus.equals(MissionStatus.READY)) {
            logger.debug("{} started", this);
            try {
                missionStatus = MissionStatus.IN_PROGRESS;
                Thread.sleep(distance * 1000);
                if (new Random().nextInt(100) < 5) {
                    missionStatus = MissionStatus.FAILED;
                    logger.debug("{} failed", this);
                    for (CrewMember crewMember : assignedCrew) {
                        CrewServiceImpl.getInstance().deleteCrewMember(crewMember);
                    }
                    SpaceshipServiceImpl.getInstance().deleteSpaceship(assignedSpaceship);
                } else {
                    missionStatus = MissionStatus.COMPLETED;
                    logger.debug("{} completed", this);
                    for (CrewMember crewMember : assignedCrew) {
                        crewMember.setReadyForNextMission(true);
                    }
                    assignedSpaceship.setReadyForNextMission(true);
                }
            } catch (InterruptedException | IOException | EntityNotFoundException e) {
                logger.error(e.getMessage());
            }
        } else {
            missionStatus = MissionStatus.CANCELLED;
            logger.debug("{} cancelled", this);
        }
    }

    @Override
    public String toString() {
        return "FlightMission{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", distance=" + distance +
                ", from=" + from +
                ", to=" + to +
                ", startDate=" + startDate.format(DateTimeFormatter.ofPattern(
                ApplicationProperties.getInstance().getDateTimeFormat())) +
                ", endDate=" + endDate.format(DateTimeFormatter.ofPattern(
                ApplicationProperties.getInstance().getDateTimeFormat())) +
                ", assignedSpaceship=" + assignedSpaceship +
                ", assignedCrew=" + assignedCrew +
                ", missionStatus=" + missionStatus +
                '}';
    }

    class FlightMissionExecutor extends TimerTask {
        public void run() {
            FlightMission.this.run();
        }
    }
}

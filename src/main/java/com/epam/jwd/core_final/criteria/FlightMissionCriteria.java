package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship assignedSpaceship;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;
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
                (assignedCrew == null || mission.getAssignedCrew().equals(assignedCrew)) &&
                (assignedSpaceship == null || mission.getAssignedSpaceship().equals(assignedSpaceship)) &&
                (missionResult == null || mission.getMissionResult().equals(missionResult));
    }

    public static FlightMissionCriteriaBuilder newBuilder() {
        return new FlightMissionCriteria().new FlightMissionCriteriaBuilder();
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
                ", missionResult=" + missionResult +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public class FlightMissionCriteriaBuilder extends Criteria<FlightMission>.Builder {

        private FlightMissionCriteriaBuilder() {
        }

        public FlightMissionCriteriaBuilder setStartDate(LocalDate startDate) {
            FlightMissionCriteria.this.startDate = startDate;
            return this;
        }

        public FlightMissionCriteriaBuilder setEndDate(LocalDate endDate) {
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

        public FlightMissionCriteriaBuilder setFrom(Planet from){
            FlightMissionCriteria.this.from = from;
            return this;
        }

        public FlightMissionCriteriaBuilder setTo(Planet to){
            FlightMissionCriteria.this.to = to;
            return this;
        }

        @Override
        public Criteria<FlightMission> build() {
            FlightMissionCriteria criteria = new FlightMissionCriteria();
            criteria.name = FlightMissionCriteria.this.name;
            criteria.id = FlightMissionCriteria.this.id;
            criteria.startDate = FlightMissionCriteria.this.startDate;
            criteria.endDate = FlightMissionCriteria.this.endDate;
            criteria.distance = FlightMissionCriteria.this.distance;
            criteria.assignedSpaceship = FlightMissionCriteria.this.assignedSpaceship;
            criteria.assignedCrew = FlightMissionCriteria.this.assignedCrew;
            criteria.missionResult = FlightMissionCriteria.this.missionResult;
            criteria.from = FlightMissionCriteria.this.from;
            criteria.to = FlightMissionCriteria.this.to;
            return criteria;
        }
    }
}

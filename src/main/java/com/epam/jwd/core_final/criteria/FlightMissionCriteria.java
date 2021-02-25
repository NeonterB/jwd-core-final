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

    private FlightMissionCriteria() {
    }

    public FlightMissionCriteriaBuilder newBuilder() {
        return new FlightMissionCriteria().new FlightMissionCriteriaBuilder();
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
            return criteria;
        }
    }
}

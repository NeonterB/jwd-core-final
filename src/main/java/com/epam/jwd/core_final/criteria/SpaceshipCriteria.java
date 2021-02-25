package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMission;

    private SpaceshipCriteria() {
    }

    public SpaceshipCriteriaBuilder newBuilder() {
        return new SpaceshipCriteria().new SpaceshipCriteriaBuilder();
    }

    public class SpaceshipCriteriaBuilder extends Criteria<Spaceship>.Builder {

        private SpaceshipCriteriaBuilder() {
        }

        public SpaceshipCriteriaBuilder setCrew(Map<Role, Short> crew) {
            SpaceshipCriteria.this.crew = crew;
            return this;
        }

        public SpaceshipCriteriaBuilder setFlightDistance(Long distance) {
            SpaceshipCriteria.this.flightDistance = distance;
            return this;
        }

        public SpaceshipCriteriaBuilder setReadiness(Boolean readiness){
            SpaceshipCriteria.this.isReadyForNextMission = readiness;
            return this;
        }

        @Override
        public Criteria<Spaceship> build() {
            SpaceshipCriteria criteria = new SpaceshipCriteria();
            criteria.name = SpaceshipCriteria.this.name;
            criteria.id = SpaceshipCriteria.this.id;
            criteria.crew = SpaceshipCriteria.this.crew;
            criteria.flightDistance = SpaceshipCriteria.this.flightDistance;
            return criteria;
        }
    }
}

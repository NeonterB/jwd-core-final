package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMission;

    private CrewMemberCriteria() {
    }

    @Override
    public boolean meetsCriteria(CrewMember member) {
        return super.meetsCriteria(member) &&
                (role == null || member.getRole().equals(role)) &&
                (rank == null || member.getRank().equals(rank)) &&
                (isReadyForNextMission == null || member.isReadyForNextMission().equals(isReadyForNextMission));
    }

    public CrewMemberCriteriaBuilder newBuilder() {
        return new CrewMemberCriteria().new CrewMemberCriteriaBuilder();
    }

    public class CrewMemberCriteriaBuilder extends Criteria<CrewMember>.Builder {

        private CrewMemberCriteriaBuilder() {
        }

        public CrewMemberCriteriaBuilder setRole(Role role) {
            CrewMemberCriteria.this.role = role;
            return this;
        }

        public CrewMemberCriteriaBuilder setRank(Rank rank) {
            CrewMemberCriteria.this.rank = rank;
            return this;
        }

        public CrewMemberCriteriaBuilder setReadiness(Boolean readiness) {
            CrewMemberCriteria.this.isReadyForNextMission = readiness;
            return this;
        }

        @Override
        public Criteria<CrewMember> build() {
            CrewMemberCriteria criteria = new CrewMemberCriteria();
            criteria.name = CrewMemberCriteria.this.name;
            criteria.id = CrewMemberCriteria.this.id;
            criteria.role = CrewMemberCriteria.this.role;
            criteria.rank = CrewMemberCriteria.this.rank;
            return criteria;
        }
    }
}

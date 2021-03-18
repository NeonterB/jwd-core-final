package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;

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

    public static CrewMemberCriteriaBuilder newBuilder() {
        return new CrewMemberCriteria().new CrewMemberCriteriaBuilder();
    }

    public static Criteria<CrewMember> parseCriteria(String line) throws IllegalArgumentException, UnknownEntityException {
        CrewMemberCriteriaBuilder builder = newBuilder();

        String[] args = line.split(";");

        for (String s : args) {
            String[] pair = s.split("=");
            if (pair[0].equalsIgnoreCase("role")) {
                builder = builder.setRole(Role.resolveRoleById(Long.parseLong(pair[1])));
            } else if (pair[0].equalsIgnoreCase("rank")) {
                builder = builder.setRank(Rank.resolveRankById(Long.parseLong(pair[1])));
            } else if (pair[0].equalsIgnoreCase("isReady")) {
                builder = builder.setReadiness(Boolean.parseBoolean(pair[1]));
            } else if (pair[0].equalsIgnoreCase("name")) {
                builder = (CrewMemberCriteriaBuilder) builder.setName(pair[1]);
            } else if (pair[0].equalsIgnoreCase("id")) {
                builder = (CrewMemberCriteriaBuilder) builder.setId(Long.parseLong(pair[1]));
            } else{
                throw new IllegalArgumentException("Unknown field - " + pair[0]);
            }
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "CrewMemberCriteria{" +
                "role=" + role +
                ", rank=" + rank +
                ", isReadyForNextMission=" + isReadyForNextMission +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
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

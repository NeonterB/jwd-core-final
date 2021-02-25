package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    private final Role role;
    private final Rank rank;
    private Boolean isReadyForNextMission = true;

    CrewMember(Role role, String name, Rank rank) {
        super(name);
        this.role = role;
        this.rank = rank;
    }
    //todo
}

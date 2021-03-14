package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.service.CrewService;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    CrewMember(@NotNull Role role, String name, Rank rank) {
        super(name);
        this.role = role;
        this.rank = rank;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean isReadyForNextMission() {
        return isReadyForNextMission;
    }

    public void setReadyForNextMission(Boolean readyForNextMission) {
        isReadyForNextMission = readyForNextMission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", role=" + role +
                ", rank=" + rank +
                ", isReadyForNextMission=" + isReadyForNextMission +
                "}";
    }
}

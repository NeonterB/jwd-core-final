package com.epam.jwd.core_final.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMission = true;

    Spaceship(@NotNull String name, Long flightDistance, Map<Role, Short> crew) throws IllegalArgumentException{
        super(name);
        this.crew = crew;
        if (flightDistance < 0) throw new IllegalArgumentException("flight distance must be positive");
        this.flightDistance = flightDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean isReadyForNextMission() {
        return isReadyForNextMission;
    }

    public void setReadyForNextMission(Boolean readyForNextMission) {
        isReadyForNextMission = readyForNextMission;
    }

    public String convertMapToString(Map<Role, Short> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Role key : map.keySet()) {
            mapAsString.append(key.getId() + ":" + map.get(key) + ",");
        }
        mapAsString.delete(mapAsString.length() - 1, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", flightDistance=" + flightDistance +
                ", isReadyForNextMission=" + isReadyForNextMission +
                ", crew=" + crew +
                "}";
    }
}

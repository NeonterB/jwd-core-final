package com.epam.jwd.core_final.domain;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship>{
    /**
     * create new {@link Spaceship}
     * @param args {@link String} - name, {@link Long} - flightDistance, {@link Map} - crew
     * @return brand new {@link Spaceship}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Spaceship create(Object... args) {
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        return new Spaceship((String) args[0], (Long) args[1], (Map<Role, Short>) args[2]);
    }
}

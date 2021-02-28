package com.epam.jwd.core_final.domain;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship>{
    @Override
    public Spaceship create(Object... args) {
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        //noinspection unchecked
        return new Spaceship((String) args[0], (Long) args[1], (Map<Role, Short>) args[2]);
    }
}

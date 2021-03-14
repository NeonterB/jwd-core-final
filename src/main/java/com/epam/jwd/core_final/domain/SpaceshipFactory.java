package com.epam.jwd.core_final.domain;

import java.util.Map;
import java.util.TreeMap;

public class SpaceshipFactory implements EntityFactory<Spaceship> {
    /**
     * create new {@link Spaceship}
     *
     * @param args {@link String} - name, {@link Long} - flightDistance, {@link Map} - crew
     * @return brand new {@link Spaceship}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Spaceship create(Object... args) throws IllegalArgumentException, ClassCastException {
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        return new Spaceship((String) args[0], (Long) args[1], (Map<Role, Short>) args[2]);
    }

    public Spaceship create(String line) throws IllegalArgumentException, ClassCastException {
        String[] args = line.split(";");
        //Map parsing
        args[2] = args[2].substring(1, args[2].length() - 1);
        String[] pairs = args[2].split(",");
        Map<Role, Short> crew = new TreeMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            crew.put(Role.resolveRoleById(Long.parseLong(keyValue[0])), Short.valueOf(keyValue[1]));
        }
        return create(args[0], Long.valueOf(args[1]), crew);
    }
}

package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.io.ReadStrategy;

import java.io.*;
import java.util.*;

public class SpaceshipReader implements ReadStrategy<Spaceship> {

    @Override
    public Collection<Spaceship> readEntities() throws IOException {
        Collection<Spaceship> spaceships = new LinkedHashSet<>();
        SpaceshipFactory factory = new SpaceshipFactory();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        ApplicationProperties.getInstance().getSpaceshipFileDir()
                ))
        ))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                while (line.charAt(0) == '#') {
                    line = reader.readLine();
                }
                String[] args = line.split(";");
                if (args.length != 3) throw new IOException("Bad input file - spaceships");
                //Map parsing
                args[2] = args[2].substring(1, args[2].length() - 1);
                String[] pairs = args[2].split(",");
                Map<Role, Short> crew = new HashMap<>();
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    crew.put(Role.resolveRoleById(Integer.parseInt(keyValue[0])), Short.valueOf(keyValue[1]));
                }

                spaceships.add(factory.create(args[0], Long.valueOf(args[1]), crew));
            }
        }
        return spaceships;
    }
}

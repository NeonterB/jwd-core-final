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
                spaceships.add(factory.create(line));
            }
        }
        return spaceships;
    }
}

package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.PlanetFactory;
import com.epam.jwd.core_final.io.ReadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class SpaceMapReader implements ReadStrategy<Planet> {
    @Override
    public Collection<Planet> readEntities() throws IOException {
        Collection<Planet> planets = new HashSet<>();
        PlanetFactory factory = new PlanetFactory();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        ApplicationProperties.getInstance().getSpaceMapFileDir()
                ))
        ))) {
            int i = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine(), i++) {
                while (line.charAt(0) == '#') {
                    line = reader.readLine();
                }
                String[] planetLine = line.split(",");

                for (int j = 0; j< planetLine.length; j++) {
                    if (!planetLine[j].equals("null")){
                        planets.add(factory.create(planetLine[j], i, j));
                    }
                }
            }
        }
        return planets;
    }
}

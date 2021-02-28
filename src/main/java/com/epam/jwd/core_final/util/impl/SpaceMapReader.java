package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.PlanetFactory;
import com.epam.jwd.core_final.util.ReadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;

public class SpaceMapReader implements ReadStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SpaceMapReader.class);

    @Override
    public Collection<Planet> readEntities(InputStream input) throws IOException {
        Collection<Planet> planets = new HashSet<>();
        PlanetFactory factory = new PlanetFactory();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
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
        } catch (IOException e) {
            throw e;
        }
        return planets;
    }
}

package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.util.ReadStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SpaceMapReader implements ReadStrategy {
    @Override
    public Collection<Planet> readEntities(File input) {
        Collection<Planet> planets = new HashSet<>();
        PlanetFactory factory = new PlanetFactory();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
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
            //todo
        }
        return planets;
    }
}

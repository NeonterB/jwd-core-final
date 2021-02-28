package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.epam.jwd.core_final.util.ReadStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpaceshipReader implements ReadStrategy {
    @Override
    public Collection<Spaceship> readEntities(File input) {
        Collection<Spaceship> spaceships = new HashSet<>();
        SpaceshipFactory factory = new SpaceshipFactory();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                while (line.charAt(0) == '#') {
                    line = reader.readLine();
                }
                String[] args = line.split(";");

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
        } catch (IOException e) {
            //todo
        }
        return spaceships;
    }

    public static void main(String[] args) {
        PropertyReaderUtil.loadProperties();
        System.out.println(new SpaceshipReader().readEntities(new File(ApplicationProperties.getInstance().getFullSpaceshipDir())));
    }
}

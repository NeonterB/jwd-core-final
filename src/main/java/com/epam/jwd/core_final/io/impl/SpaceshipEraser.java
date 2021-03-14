package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.io.EraseStrategy;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class SpaceshipEraser implements EraseStrategy<Spaceship> {
    @Override
    public void erase(Spaceship entity) throws EntityNotFoundException, IOException {
        Queue<String> lines = new LinkedList<>();
        String line = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        ApplicationProperties.getInstance().getSpaceshipFileDir()
                ))
        ))) {
            String spaceshipToDelete = entity.getName() + ';' +
                    entity.getFlightDistance() + ';' +
                    SpaceshipWriter.mapToString(entity.getCrew());
            line = reader.readLine();
            while (line.charAt(0) == '#') {
                lines.add(line);
                line = reader.readLine();
            }

            boolean wasFound = false;
            while (line != null) {
                if (!line.equalsIgnoreCase(spaceshipToDelete)) {
                    lines.add(line);
                } else wasFound = true;
                line = reader.readLine();
            }
            if (!wasFound) throw new EntityNotFoundException("Spaceship", entity);
        }

        try (FileWriter writer = new FileWriter(new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource(
                        ApplicationProperties.getInstance().getSpaceshipFileDir()
                )).getPath()
        ), false)) {
            while (!lines.isEmpty()) {
                writer.write(lines.poll());
                writer.write("\n");
            }
        }
    }
}

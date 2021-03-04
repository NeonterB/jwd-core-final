package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.io.WriteStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpaceshipWriter implements WriteStrategy<Spaceship> {
    @Override
    public void writeEntity(Spaceship spaceship) throws IOException {
        try (FileWriter writer = new FileWriter(new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource(
                        ApplicationProperties.getInstance().getSpaceshipFileDir()
                )).getPath()
        ), true)) {
            writer.write(
                    "\n" + spaceship.getName() + ";" +
                            spaceship.getFlightDistance() + ";" +
                            mapToString(spaceship.getCrew())
            );
        }
    }

    public static String mapToString(Map<Role, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Role key : map.keySet()) {
            mapAsString.append(key.getId()).append(":").append(map.get(key)).append(",");
        }
        mapAsString.delete(mapAsString.length() - 1, mapAsString.length()).append("}");
        return mapAsString.toString();
    }
}

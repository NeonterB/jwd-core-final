package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightMissionFactory implements EntityFactory<FlightMission> {
    /**
     * create new {@link FlightMission}
     *
     * @param args {@link String} - name, {@link LocalDateTime} - startDate, {@link Planet} - from, {@link Planet} - to
     * @return brand new {@link FlightMission}
     */
    @Override
    public FlightMission create(Object... args) throws IllegalArgumentException, ClassCastException {
        if (args.length != 4) throw new IllegalArgumentException("FlightMission creation requires 4 arguments");
        return new FlightMission((String) args[0], (LocalDateTime) args[1], (Planet) args[2], (Planet) args[3]);
    }

    public FlightMission create(String line) throws ClassCastException, IllegalArgumentException, EntityNotFoundException {
        String[] args = line.split(",");
        if (args.length != 4) throw new IllegalArgumentException("FlightMission creation requires 4 arguments");
        long id1 = Long.parseLong(args[2]);
        long id2 = Long.parseLong(args[3]);
        return create(
                args[0],
                LocalDateTime.parse(args[1], DateTimeFormatter.ofPattern(
                        ApplicationProperties.getInstance().getDateTimeFormat())
                ),
                SpacemapServiceImpl.getInstance().findPlanetById(id1).orElseThrow(() -> new EntityNotFoundException("Planet", id1)),
                SpacemapServiceImpl.getInstance().findPlanetById(id2).orElseThrow(() -> new EntityNotFoundException("Planet", id2))
        );
    }
}

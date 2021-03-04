package com.epam.jwd.core_final.domain;

import java.time.LocalDateTime;

public class FlightMissionFactory implements EntityFactory<FlightMission>{
    /**
     * create new {@link FlightMission}
     * @param args {@link String} - name, {@link LocalDateTime} - startDate, {@link Planet} - from, {@link Planet} - to
     * @return brand new {@link FlightMission}
     */
    @Override
    public FlightMission create(Object... args) {
        if (args.length != 4) throw new IllegalArgumentException("FlightMission creation requires 4 arguments");
        return new FlightMission((String) args[0], (LocalDateTime) args[1], (Planet) args[2], (Planet) args[3]);
    }
}

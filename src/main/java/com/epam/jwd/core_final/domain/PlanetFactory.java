package com.epam.jwd.core_final.domain;

public class PlanetFactory implements EntityFactory<Planet>{
    /**
     * creates new {@link Planet}
     * @param args {@link String} - name, {@link Integer} - x coordinate, {@link Integer} - y coordinate
     * @return brand new {@link Planet}
     */
    @Override
    public Planet create(Object... args){
        if (args.length != 3) throw new IllegalArgumentException("Planet creation requires 3 arguments - name, Point.x, Point.y");
        return new Planet((String) args[0], (Integer) args[1], (Integer) args[2]);
    }
}

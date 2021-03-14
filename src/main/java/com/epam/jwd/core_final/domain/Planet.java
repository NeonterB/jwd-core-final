package com.epam.jwd.core_final.domain;

import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * location {@link java.util.Map} - planet coordinate in the universe
 */
public class Planet extends AbstractBaseEntity{
    public class Point{
        private Integer x;
        private Integer y;

        private Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }
    }

    private final Point location;

    Planet(String name, Integer x, Integer y) {
        super(name);
        this.location = new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Planet planet = (Planet) o;
        return Objects.equals(location, planet.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), location);
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", x=" + location.x +
                ", y=" + location.y +
                '}';
    }
}

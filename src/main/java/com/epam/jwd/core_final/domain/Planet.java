package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * location {@link java.util.Map} - planet coordinate in the universe
 */
public class Planet extends AbstractBaseEntity{
    private static class Point{
        private Integer x;
        private Integer y;

        public Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
    }

    private Point location;

    Planet(String name, Integer x, Integer y) {
        super(name);
        this.location = new Point(x, y);
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name=" + getName() +
                ", x=" + location.x +
                ", y=" + location.y +
                '}';
    }
}

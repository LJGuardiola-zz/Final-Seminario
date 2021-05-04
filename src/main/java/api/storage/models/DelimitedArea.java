package api.storage.models;

import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class DelimitedArea {

    @NotNull(message = "La condenada no puede ser nula.")
    private Coordinate coordinate;

    @NotNegative(message = "El radio no puede ser negativo.")
    private float radius;

    public DelimitedArea(Coordinate coordinate, float radius) {
        this.coordinate = coordinate;
        this.radius = radius;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getLatitude() {
        return coordinate.getLatitude();
    }

    public double getLongitude() {
        return coordinate.getLongitude();
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}

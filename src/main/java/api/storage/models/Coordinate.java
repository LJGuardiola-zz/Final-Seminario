package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class Coordinate {

    @NotNull(message = "La latitud no puede ser nulo.")
    @NotEmpty(message = "La latitud no puede ser vacío.")
    private double latitude;

    @NotNull(message = "La longitud no puede ser nulo.")
    @NotEmpty(message = "La longitud no puede ser vacío.")
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}

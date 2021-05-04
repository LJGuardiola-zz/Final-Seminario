package api.dto;

import api.storage.models.OpenSpace;

public class DtoOpenSpace {

    private final int id;
    private final String name;
    private final boolean available;
    private final int capacity;
    private final double latitude;
    private final double longitude;
    private final double radius;
    private final DtoCategory category;
    private final DtoCampaign entryCampaign;
    private final DtoCampaign exitCampaign;

    public DtoOpenSpace(OpenSpace space) {
        this.id = space.getID();
        this.name = space.getName();
        this.available = space.isAvailable();
        this.capacity = space.getCapacity();
        this.latitude = space.getArea().getLatitude();
        this.longitude = space.getArea().getLongitude();
        this.radius = space.getArea().getRadius();
        this.category = new DtoCategory(space.getCategory());
        this.entryCampaign = new DtoCampaign(space.getEntryCampaign());
        this.exitCampaign = new DtoCampaign(space.getExitCampaign());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }

    public DtoCategory getCategory() {
        return category;
    }

    public DtoCampaign getEntryCampaign() {
        return entryCampaign;
    }

    public DtoCampaign getExitCampaign() {
        return exitCampaign;
    }

}

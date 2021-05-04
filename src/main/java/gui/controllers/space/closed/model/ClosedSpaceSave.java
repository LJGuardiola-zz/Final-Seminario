package gui.controllers.space.closed.model;

import api.dto.DtoCampaign;
import api.dto.DtoCategory;
import api.dto.DtoCity;
import api.dto.DtoPerson;
import api.storage.models.Person;
import io.datafx.controller.injection.scopes.FlowScoped;

@FlowScoped
public class ClosedSpaceSave {

    private boolean assign;
    private String name;
    private boolean available;
    private String street;
    private DtoCity city;
    private String latitude;
    private String longitude;
    private String radius;
    private String capacity;
    private DtoCategory category;
    private DtoCampaign entryCampaign;
    private DtoCampaign exitCampaign;
    private DtoPerson responsible;

    private static final ClosedSpaceSave INSTANCE = new ClosedSpaceSave();

    public static ClosedSpaceSave getInstance() {
        return INSTANCE;
    }

    public boolean isAssign() {
        return assign;
    }

    public void setAssign(boolean assign) {
        this.assign = assign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public DtoCity getCity() {
        return city;
    }

    public void setCity(DtoCity city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public DtoCategory getCategory() {
        return category;
    }

    public void setCategory(DtoCategory category) {
        this.category = category;
    }

    public DtoCampaign getEntryCampaign() {
        return entryCampaign;
    }

    public void setEntryCampaign(DtoCampaign entryCampaign) {
        this.entryCampaign = entryCampaign;
    }

    public DtoCampaign getExitCampaign() {
        return exitCampaign;
    }

    public void setExitCampaign(DtoCampaign exitCampaign) {
        this.exitCampaign = exitCampaign;
    }

    public DtoPerson getResponsible() {
        return responsible;
    }

    public void setResponsible(DtoPerson responsible) {
        this.responsible = responsible;
    }

}

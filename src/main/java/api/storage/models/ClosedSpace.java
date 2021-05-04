package api.storage.models;

import net.sf.oval.constraint.NotNull;

public class ClosedSpace extends Space {

    @NotNull(message = "La dirección no puede ser nula.")
    private Address address;

    @NotNull(message = "El responsable no puede ser nulo.")
    private Person responsible;

    public ClosedSpace(String name, boolean available, int capacity, DelimitedArea area, SpaceCategory category, SpaceCampaign entryCampaign, SpaceCampaign exitCampaign, Address address, Person responsible) {
        this(null, name, available, capacity, area, category, entryCampaign, exitCampaign, address, responsible);
    }

    public ClosedSpace(Integer ID, String name, boolean available, int capacity, DelimitedArea area, SpaceCategory category, SpaceCampaign entryCampaign, SpaceCampaign exitCampaign, Address address, Person responsible) {
        super(ID, name, available, capacity, area, category, entryCampaign, exitCampaign);
        this.address = address;
        this.responsible = responsible;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

}

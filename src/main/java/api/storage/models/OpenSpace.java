package api.storage.models;

public class OpenSpace extends Space {

    public OpenSpace(String name, boolean available, int capacity, DelimitedArea area, SpaceCategory category, SpaceCampaign entryCampaign, SpaceCampaign exitCampaign) {
        this(null, name, available, capacity, area, category, entryCampaign, exitCampaign);
    }

    public OpenSpace(Integer ID, String name, boolean available, int capacity, DelimitedArea area, SpaceCategory category, SpaceCampaign entryCampaign, SpaceCampaign exitCampaign) {
        super(ID, name, available, capacity, area, category, entryCampaign, exitCampaign);
    }

}

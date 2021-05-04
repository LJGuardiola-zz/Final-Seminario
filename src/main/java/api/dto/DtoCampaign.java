package api.dto;

import api.storage.models.SpaceCampaign;

import java.util.Objects;

public class DtoCampaign {

    private final Integer id;
    private final String message;

    public DtoCampaign(SpaceCampaign campaign) {
        this.id = campaign.getID();
        this.message = campaign.getMessage();
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoCampaign that = (DtoCampaign) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

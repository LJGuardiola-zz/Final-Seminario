package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.util.Objects;

public abstract class Space {

    @Nullable
    @NotNegative(message = "El id de espacio no puede ser negativo.")
    private Integer ID;

    @NotNull(message = "El nombre del espacio no puede ser nulo.")
    @NotEmpty(message = "El nombre del espacio no puede ser vacío.")
    private String name;

    private boolean available;

    @NotNull(message = "La capacidad no puede ser nulo.")
    @NotNegative(message = "La capacidad no puede ser negativo.")
    private int capacity;

    @NotNull(message = "El area no puede ser nulo.")
    private DelimitedArea area;

    @NotNull(message = "La categoría no puede ser nulo")
    private SpaceCategory category;

    @NotNull(message = "La campaña de entrada no puede ser nula.")
    private SpaceCampaign entryCampaign;

    @NotNull(message = "La campaña de salida no puede ser nula.")
    private SpaceCampaign exitCampaign;

    public Space(Integer ID, String name, boolean available, int capacity, DelimitedArea area, SpaceCategory category, SpaceCampaign entryCampaign, SpaceCampaign exitCampaign) {
        this.ID = ID;
        this.name = name;
        this.available = available;
        this.capacity = capacity;
        this.area = area;
        this.category = category;
        this.entryCampaign = entryCampaign;
        this.exitCampaign = exitCampaign;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isUnavailable() {
        return !available;
    }

    public void setAvailable() {
        available = true;
    }

    public void setUnavailable() {
        available = false;
    }

    public DelimitedArea getArea() {
        return area;
    }

    public void setArea(DelimitedArea area) {
        this.area = area;
    }

    public SpaceCategory getCategory() {
        return category;
    }

    public void setCategory(SpaceCategory category) {
        this.category = category;
    }

    public SpaceCampaign getEntryCampaign() {
        return entryCampaign;
    }

    public void setEntryCampaign(SpaceCampaign entryCampaign) {
        this.entryCampaign = entryCampaign;
    }

    public SpaceCampaign getExitCampaign() {
        return exitCampaign;
    }

    public void setExitCampaign(SpaceCampaign exitCampaign) {
        this.exitCampaign = exitCampaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return Objects.equals(ID, space.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}

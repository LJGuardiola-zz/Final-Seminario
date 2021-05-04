package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.util.Objects;

public class SpaceCategory {

    @Nullable
    @NotNegative(message = "El ID de la categoría de un espacio no puede ser nulo.")
    private Integer ID;

    @NotNull(message = "El nombre de la categoría de un espacio no puede ser nulo.")
    @NotEmpty(message = "El nombre de la categoría de un espacio no puede estar vacío.")
    private String name;

    @NotEmpty(message = "La descripción de la categoría de un espacio no puede estar vacío.")
    private String description;

    public SpaceCategory(String name, String description) {
        this(null, name, description);
    }

    public SpaceCategory(Integer ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceCategory category = (SpaceCategory) o;
        return Objects.equals(ID, category.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}

package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class SpaceCampaign {

    @Nullable
    @NotNegative(message = "El ID de la campaña no puede ser negativo.")
    private Integer ID;

    @NotNull(message = "El mensaje de la campaña no puede ser nulo.")
    @NotEmpty(message = "El mensaje de la campaña no puede estar vacío.")
    private String message;

    public SpaceCampaign(String message) {
        this(null, message);
    }

    public SpaceCampaign(Integer ID, String message) {
        this.ID = ID;
        this.message = message;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

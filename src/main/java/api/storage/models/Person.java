package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotNegative;

public abstract class Person {

    @Nullable
    @NotNegative(message = "El ID de una persona no puede ser negativo.")
    private Integer ID;

    public Person(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public abstract String getCUI();

    public abstract String getName();

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                '}';
    }

}

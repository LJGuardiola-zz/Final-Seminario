package api.dto;

import api.storage.models.Permission;

public class DtoPermission {

    private final Integer ID;

    private final String code;

    private final String description;

    public DtoPermission(Permission permission) {
        this(
                permission.getID(),
                permission.getCode(),
                permission.getDescription()
        );
    }

    public DtoPermission(Integer ID, String code, String description) {
        this.ID = ID;
        this.code = code;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "DtoPermission{" +
                "ID=" + ID +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}

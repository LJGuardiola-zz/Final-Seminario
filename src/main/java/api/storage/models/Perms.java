package api.storage.models;

public enum Perms {

    role_create("role_create"), role_read("role_read"), role_update("role_update"), role_delete("role_delete"),
    user_create("user_create"), user_read("user_read"), user_update("user_update"), user_delete("user_delete"),
    person_create("person_create"), person_read("person_read"), person_update("person_update"), person_delete("person_delete"),
    category_create("category_create"), category_read("category_read"), category_update("category_update"), category_delete("category_delete"),
    campaign_create("campaign_create"), campaign_read("campaign_read"), campaign_update("campaign_update"), campaign_delete("campaign_delete"),
    space_create("space_create"), space_read("space_read"), space_update("space_update"), space_delete("space_delete");

    private final String code;

    Perms(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

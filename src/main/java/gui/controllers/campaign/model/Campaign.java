package gui.controllers.campaign.model;

import api.dto.DtoCampaign;
import api.dto.DtoRole;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Campaign {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty message;

    public Campaign(DtoCampaign campaign) {
        id = new SimpleIntegerProperty(campaign.getId());
        message = new SimpleStringProperty(campaign.getMessage());
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

}

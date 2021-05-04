package gui.controllers.campaign.model;

import api.dto.DtoCampaign;
import api.dto.DtoRole;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class CampaignDataModel {

    protected final ObjectProperty<ObservableList<Campaign>> data = new SimpleObjectProperty<>();

    private Campaign selected;

    public ObjectProperty<ObservableList<Campaign>> getData() {
        return data;
    }

    public Campaign getSelected() {
        return selected;
    }

    public void setSelected(Campaign campaign) {
        selected = campaign;
    }

    public void load(List<DtoCampaign> campaigns) {
        data.set(
                campaigns.stream().map(Campaign::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(Campaign campaign) {
        data.get().remove(campaign);
    }

}

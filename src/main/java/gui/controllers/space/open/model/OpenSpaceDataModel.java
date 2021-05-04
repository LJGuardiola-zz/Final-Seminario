package gui.controllers.space.open.model;

import api.dto.DtoOpenSpace;
import api.dto.DtoSpace;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class OpenSpaceDataModel {

    protected final ObjectProperty<ObservableList<OpenSpace>> data = new SimpleObjectProperty<>();

    private OpenSpace selected;

    public ObjectProperty<ObservableList<OpenSpace>> getData() {
        return data;
    }

    public OpenSpace getSelected() {
        return selected;
    }

    public void setSelected(OpenSpace openSpace) {
        selected = openSpace;
    }

    public void load(List<DtoOpenSpace> spaces) {
        data.set(
                spaces.stream().map(OpenSpace::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(OpenSpace openSpace) {
        data.get().remove(openSpace);
    }

}

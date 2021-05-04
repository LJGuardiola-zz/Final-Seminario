package gui.controllers.space.closed.model;

import api.dto.DtoClosedSpace;
import api.dto.DtoOpenSpace;
import gui.controllers.space.open.model.OpenSpace;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class ClosedSpaceDataModel {

    protected final ObjectProperty<ObservableList<ClosedSpace>> data = new SimpleObjectProperty<>();

    private ClosedSpace selected;

    public ObjectProperty<ObservableList<ClosedSpace>> getData() {
        return data;
    }

    public ClosedSpace getSelected() {
        return selected;
    }

    public void setSelected(ClosedSpace closedSpace) {
        selected = closedSpace;
    }

    public void load(List<DtoClosedSpace> spaces) {
        data.set(
                spaces.stream().map(ClosedSpace::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(ClosedSpace closedSpace) {
        data.get().remove(closedSpace);
    }

}

package gui.controllers.category.model;

import api.dto.DtoCategory;
import api.dto.DtoUser;
import gui.controllers.user.model.User;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class CategoryDataModel {

    protected final ObjectProperty<ObservableList<Category>> data = new SimpleObjectProperty<>();

    private Category selected;

    public ObjectProperty<ObservableList<Category>> getData() {
        return data;
    }

    public Category getSelected() {
        return selected;
    }

    public void setSelected(Category category) {
        selected = category;
    }

    public void load(List<DtoCategory> categories) {
        data.set(
                categories.stream().map(Category::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(Category category) {
        data.get().remove(category);
    }

}

package gui.controllers.category;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.category.model.Category;
import gui.controllers.category.model.CategoryDataModel;
import gui.controllers.global.Session;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/category/listing.fxml", title = "Listado de categorías")
public class CategoryListController extends InnerController {

    @ViewNode
    private HBox btnContainer;

    @ViewNode
    @LinkAction(CategoryCreateController.class)
    private JFXButton newButton;

    @ViewNode
    @LinkAction(CategoryEditController.class)
    private JFXButton editButton;

    @ViewNode
    @ActionTrigger("delete")
    private JFXButton deleteButton;

    @ViewNode
    private TableView<Category> table;

    @FXML
    private TableColumn<Category, Integer> idColumn;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private TableColumn<Category, String> descriptionColumn;

    private CategoryDataModel dataModel;

    private final Session session = Session.getInstance();

    @PostConstruct
    private void init() {

        ApplicationContext.getInstance().register(dataModel = new CategoryDataModel(), CategoryDataModel.class);

        if (!session.hasPermission("category_update")) {
            btnContainer.getChildren().remove(editButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                editButton.setDisable(newValue == null);
            });
        }

        if (!session.hasPermission("category_delete")) {
            btnContainer.getChildren().remove(deleteButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                deleteButton.setDisable(newValue == null);
            });
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.itemsProperty().bindBidirectional(dataModel.getData());
        table.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> dataModel.setSelected(newValue)
        );

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().category().getAll())
                .addConsumerInPlatformThread(dataModel::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("delete")
    private void delete() {
        Category category = dataModel.getSelected();
        confirm("¿Seguro desea eliminar la categoría \"" + category.getName() + "\"", () -> {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().category().delete(category.getId()))
                    .addRunnableInPlatformThread(() -> dataModel.delete(category))
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });
    }

}

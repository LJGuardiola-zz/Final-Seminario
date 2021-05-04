package gui.controllers.space.open;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.global.Session;
import gui.controllers.space.open.model.OpenSpace;
import gui.controllers.space.open.model.OpenSpaceDataModel;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/space/open/list.fxml", title = "Listado de espacios abiertos")
public class OpenSpaceListController extends InnerController {

    @ViewNode
    private HBox btnContainer;

    @ViewNode
    @LinkAction(OpenSpaceCreateController.class)
    private JFXButton newButton;

    @ViewNode
    @LinkAction(OpenSpaceEditController.class)
    private JFXButton editButton;

    @ViewNode
    @ActionTrigger("delete")
    private JFXButton deleteButton;

    @ViewNode
    private TableView<OpenSpace> table;

    @FXML
    private TableColumn<OpenSpace, Integer> idColumn;

    @FXML
    private TableColumn<OpenSpace, String> nameColumn;

    @FXML
    private TableColumn<OpenSpace, Boolean> availableColumn;

    @FXML
    private TableColumn<OpenSpace, Integer> capacityColumn;

    private OpenSpaceDataModel dataModel;

    private final Session session = Session.getInstance();

    @PostConstruct
    private void init() {

        ApplicationContext.getInstance().register(dataModel = new OpenSpaceDataModel(), OpenSpaceDataModel.class);

        if (!session.hasPermission("space_create")) {
            btnContainer.getChildren().remove(newButton);
        }

        if (!session.hasPermission("space_update")) {
            btnContainer.getChildren().remove(editButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                editButton.setDisable(newValue == null);
            });
        }

        if (!session.hasPermission("space_delete")) {
            btnContainer.getChildren().remove(deleteButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                deleteButton.setDisable(newValue == null);
            });
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        availableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        table.itemsProperty().bindBidirectional(dataModel.getData());
        table.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> dataModel.setSelected(newValue)
        );

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().space().getAllOpen())
                .addConsumerInPlatformThread(dataModel::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("delete")
    private void delete() {
        OpenSpace openSpace = dataModel.getSelected();
        confirm("¿Seguro desea eliminar el espacio \"" + openSpace.getName() + "\"", () -> {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().space().delete(openSpace.getId()))
                    .addRunnableInPlatformThread(() -> dataModel.delete(openSpace))
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });
    }

}

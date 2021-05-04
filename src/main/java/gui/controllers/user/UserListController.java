package gui.controllers.user;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.global.Session;
import gui.controllers.user.model.User;
import gui.controllers.user.model.UserDataModel;
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

@ViewController(value = "/gui/views/user/listing.fxml", title = "Listado de usuarios")
public class UserListController extends InnerController {

    @ViewNode
    private HBox btnContainer;

    @ViewNode
    @LinkAction(UserCreateController.class)
    private JFXButton newButton;

    @ViewNode
    @LinkAction(UserEditController.class)
    private JFXButton editButton;

    @ViewNode
    @ActionTrigger("delete")
    private JFXButton deleteButton;

    @ViewNode
    private TableView<User> table;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> stateColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private UserDataModel dataModel;

    private final Session session = Session.getInstance();

    @PostConstruct
    private void init() {

        ApplicationContext.getInstance().register(dataModel = new UserDataModel(), UserDataModel.class);

        if (!session.hasPermission("user_update")) {
            btnContainer.getChildren().remove(editButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                editButton.setDisable(newValue == null);
            });
        }

        if (!session.hasPermission("user_delete")) {
            btnContainer.getChildren().remove(deleteButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                deleteButton.setDisable(newValue == null);
            });
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        table.itemsProperty().bindBidirectional(dataModel.getData());
        table.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> dataModel.setSelected(newValue)
        );

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().user().getAll())
                .addConsumerInPlatformThread(dataModel::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("delete")
    private void delete() {
        User user = dataModel.getSelected();
        confirm("¿Seguro desea eliminar el usuario \"" + user.getUsername() + "\"", () -> {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().user().delete(user.getId()))
                    .addRunnableInPlatformThread(() -> dataModel.delete(user))
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });
    }

}

package gui.controllers.user;

import api.Api;
import api.dto.DtoRole;
import api.dto.DtoUser;
import api.dto.DtoUserState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.user.model.UserDataModel;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/user/form.fxml", title = "Editar un usuario")
public class UserEditController extends InnerController {

    @ViewNode
    private JFXTextField idField;

    @ViewNode
    private JFXTextField usernameField;

    @ViewNode
    private JFXTextField emailField;

    @ViewNode
    private JFXPasswordField passwordField;

    @ViewNode
    private JFXComboBox<DtoRole> roleField;

    @ViewNode
    private JFXComboBox<DtoUserState> stateField;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("submit")
    private JFXButton submitButton;

    private UserDataModel dataModel;

    private DtoUser userToEdit;

    @PostConstruct
    private void init() {

        dataModel = ApplicationContext.getInstance().getRegisteredObject(UserDataModel.class);

        usernameField.setDisable(true);
        emailField.setDisable(true);
        passwordField.setDisable(true);
        roleField.setDisable(true);

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().user().getUserStates())
                .addConsumerInPlatformThread(states -> stateField.getItems().addAll(states))
                .addSupplierInExecutor(() -> Api.getInstance().role().getAllAssignable())
                .addConsumerInPlatformThread(roles -> roleField.getItems().addAll(roles))
                .addSupplierInExecutor(() -> Api.getInstance().user().search(dataModel.getSelected().getId()))
                .addConsumerInPlatformThread(this::loadForm)
                .onException(Throwable::printStackTrace)
                .withFinal(this::endLoading)
                .run();

    }

    private void loadForm(DtoUser dtoUser) {

        userToEdit = dtoUser;

        idField.setText(String.valueOf(dtoUser.getID()));
        usernameField.setText(dtoUser.getUsername());
        emailField.setText(dtoUser.getEmail());
        stateField.getSelectionModel().select(
                dtoUser.getState()
        );
        if (!roleField.getItems().contains(dtoUser.getRole()))
            roleField.getItems().add(dtoUser.getRole());
        roleField.getSelectionModel().select(
                dtoUser.getRole()
        );
    }

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> {
                    DtoUserState selectedItem = stateField.getSelectionModel().getSelectedItem();
                    if (!userToEdit.getState().equals(selectedItem)) {
                        switch (selectedItem.getCode()) {
                            case 0:
                                Api.getInstance().user().disable(userToEdit.getID());
                                break;
                            case 1:
                                Api.getInstance().user().enable(userToEdit.getID());
                                break;
                            case 2:
                                Api.getInstance().user().suspend(userToEdit.getID());
                                break;
                        }
                    }
                })
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

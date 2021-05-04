package gui.controllers.user;

import api.Api;
import api.dto.DtoRole;
import api.dto.DtoUserState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/user/form.fxml", title = "Crear un usuario")
public class UserCreateController extends InnerController {

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

    @PostConstruct
    private void init() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().user().getUserStates())
                .addConsumerInPlatformThread(states -> stateField.getItems().addAll(states))
                .addSupplierInExecutor(() -> Api.getInstance().role().getAllAssignable())
                .addConsumerInPlatformThread(roles -> roleField.getItems().addAll(roles))
                .onException(Throwable::printStackTrace)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("submit")
    private void submit() {
        if (usernameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() ||
        roleField.getSelectionModel().getSelectedItem() == null || stateField.getSelectionModel().getSelectedItem() == null) {
            showMessage("Completa todos los campos");
        } else {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().user().create(
                            usernameField.getText(),
                            emailField.getText(),
                            passwordField.getText(),
                            roleField.getSelectionModel().getSelectedItem().getID(),
                            stateField.getSelectionModel().getSelectedItem().getCode()
                    ))
                    .addRunnableInPlatformThread(this::navigateBack)
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        }
    }

}

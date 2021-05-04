package gui.controllers.authentication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.global.Session;
import gui.controllers.home.HomeController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/authentication/login.fxml", title = "Iniciar sesión")
public class LoginController extends InnerController {

    @ViewNode
    private JFXTextField usernameField;

    @ViewNode
    private JFXPasswordField passwordField;

    @ViewNode
    @ActionTrigger("Submit")
    private JFXButton submitButton;

    @ViewNode
    @LinkAction(RegisterController.class)
    private JFXButton registerButton;

    private final Session session = Session.getInstance();

    @PostConstruct
    public void init() {
        usernameField.textProperty().addListener((o) -> disableSubmitIfEmpty());
        passwordField.textProperty().addListener((o) -> disableSubmitIfEmpty());
    }

    private void disableSubmitIfEmpty() {
        submitButton.setDisable( ! (
                (usernameField.getText() != null && !usernameField.getText().isEmpty()) &&
                (passwordField.getText() != null && !passwordField.getText().isEmpty())
        ));
    }

    @ActionMethod("Submit")
    public void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> session.login(usernameField.getText(), passwordField.getText()))
                .addRunnableInPlatformThread(() -> navigateTo(HomeController.class))
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

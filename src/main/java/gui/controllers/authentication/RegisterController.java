package gui.controllers.authentication;

import api.Api;
import api.dto.DtoNaturalPerson;
import api.exceptions.InvalidDataException;
import com.jfoenix.controls.*;
import gui.controllers.base.InnerController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/authentication/register.fxml", title = "Registro")
public class RegisterController extends InnerController {

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField cuilField;

    @FXML
    private JFXDatePicker birthdateField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField confirmPasswordField;

    @FXML
    @ActionTrigger("submit")
    private JFXButton submit;

    @PostConstruct
    private void init() {

        cuilField.focusedProperty().addListener((o, unSed, focused) -> {
            if (!focused) {
                ProcessChain.create()
                        .addRunnableInPlatformThread(this::startLoading)
                        .addSupplierInExecutor(() -> Api.getInstance().person().search(cuilField.getText()))
                        .addConsumerInPlatformThread(this::setValues)
                        .onException(this::showMessage)
                        .withFinal(this::endLoading)
                        .run();
            }
        });

    }

    private void setValues(DtoNaturalPerson person) {
        boolean isRegistered = person != null;
        firstNameField.setText(isRegistered ? person.getFirstName() : "");
        firstNameField.setDisable(isRegistered);
        lastNameField.setText(isRegistered ? person.getLastName() : "");
        lastNameField.setDisable(isRegistered);
        birthdateField.setValue(isRegistered ? person.getBirthdate() : null);
        birthdateField.setDisable(isRegistered);
    }

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(
                        () -> Api.getInstance().app().register(
                                firstNameField.getText(),
                                lastNameField.getText(),
                                cuilField.getText(),
                                birthdateField.getValue(),
                                usernameField.getText(),
                                emailField.getText(),
                                passwordField.getText()
                        )
                )
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(throwable -> {
                    if (throwable instanceof InvalidDataException) {
                        ((InvalidDataException) throwable).getErros().forEach(this::showMessage);
                    } else showMessage(throwable.getMessage());
                })
                .withFinal(this::endLoading)
                .run();
    }

}

package gui.controllers.citizen;

import api.Api;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/citizen/myData.fxml", title = "Mis datos")
public class MyDataController extends InnerController {

    @ViewNode
    private JFXTextField usernameField;

    @ViewNode
    private JFXTextField emailField;

    @ViewNode
    private JFXTextField firstNameField;

    @ViewNode
    private JFXTextField lastNameField;

    @ViewNode
    private JFXTextField cuilField;

    @ViewNode
    private JFXDatePicker birthdateField;

    @ViewNode
    @ActionTrigger("update")
    private JFXButton updateButton;

    @PostConstruct
    private void init() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().app().getUserLoggedData())
                .addConsumerInPlatformThread(user -> {
                    usernameField.setText(user.getUsername());
                    emailField.setText(user.getEmail());
                    firstNameField.setText(user.getPerson().getFirstName());
                    lastNameField.setText(user.getPerson().getLastName());
                    cuilField.setText(user.getPerson().getCUIL());
                    birthdateField.setValue(user.getPerson().getBirthdate());
                })
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("update")
    private void update() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().app().updateUserLoggedData(
                        firstNameField.getText(),
                        lastNameField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

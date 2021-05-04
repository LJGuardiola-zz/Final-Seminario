package gui.controllers.person;

import api.Api;
import com.jfoenix.controls.*;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/person/form.fxml", title = "Crear una persona")
public class PersonCreateController extends InnerController {

    @ViewNode
    private JFXComboBox<String> typeField;

    @ViewNode
    private JFXTextField idField;

    @ViewNode
    private JFXTextField firstnameField;

    @ViewNode
    private JFXTextField lastnameField;

    @ViewNode
    private JFXTextField cuiField;

    @ViewNode
    private JFXDatePicker birthdateField;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("submit")
    private JFXButton submitButton;

    @PostConstruct
    private void init() {

        typeField.getItems().addAll("Física", "Jurídica");
        typeField.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (newValue.equals("Física")) {
                cuiField.setPromptText("CUIL");
                lastnameField.setDisable(false);
                birthdateField.setDisable(false);
            } else {
                cuiField.setPromptText("CUIT");
                lastnameField.setDisable(true);
                birthdateField.setDisable(true);
            }
        });
        typeField.getSelectionModel().select("Física");

    }

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> {
                    if (typeField.getSelectionModel().getSelectedItem().equals("Física")) {
                        Api.getInstance().person().createNaturalPerson(
                                cuiField.getText(),
                                firstnameField.getText(),
                                lastnameField.getText(),
                                birthdateField.getValue()
                        );
                    } else {
                        Api.getInstance().person().createLegalPerson(
                                cuiField.getText(),
                                firstnameField.getText()
                        );
                    }
                })
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

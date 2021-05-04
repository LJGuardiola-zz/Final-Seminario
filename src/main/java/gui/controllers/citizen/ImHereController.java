package gui.controllers.citizen;

import api.Api;
import api.dto.DtoSpace;
import api.exceptions.NoDevicePairedException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.core.concurrent.ProcessChain;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/citizen/imHere.fxml", title = "Estoy Aquí")
public class ImHereController extends InnerController {

    @ViewNode
    private VBox imNotHereBox;

    @ViewNode
    private VBox imHereBox;

    @ViewNode
    private Label spaceLabel;

    @ViewNode
    private JFXTextField amountField;

    @ViewNode
    private JFXComboBox<DtoSpace> spaceField;

    @ViewNode
    @ActionTrigger("imNotHere")
    private JFXButton imNotHere;

    @ViewNode
    @ActionTrigger("imHere")
    private JFXButton imHere;

    @PostConstruct
    private void init() {

        spaceField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imHere.setDisable(newValue == null);
        });

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().app().getCurrentMovement())
                .addConsumerInPlatformThread(movement -> {
                    if (movement.isPresent()) {
                        spaceLabel.setText(movement.get().getSpace().getName());
                        imHereBox.setDisable(true);
                    } else {
                        imNotHereBox.setDisable(true);
                    }
                })
                .addSupplierInExecutor(() -> Api.getInstance().space().getAll())
                .addConsumerInPlatformThread(spaces -> spaceField.getItems().setAll(spaces))
                .onException(throwable -> {
                    UiExceptionHandler.handle(throwable);
                    if (throwable instanceof NoDevicePairedException) {
                        root.setDisable(true);
                    }
                })
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("imNotHere")
    private void imNotHere() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().app().imNotHere())
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("imHere")
    private void imHere() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().app().imHere(
                        spaceField.getSelectionModel().getSelectedItem().getId(),
                        amountField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

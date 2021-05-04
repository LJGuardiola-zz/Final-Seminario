package gui.controllers.device;

import api.Api;
import api.dto.DtoDeviceBrand;
import api.dto.DtoDeviceCompany;
import api.dto.DtoDeviceModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.stream.Collectors;

@ViewController(value = "/gui/views/device/form.fxml", title = "Vincular nuevo dispositivo")
public class DeviceCreateController extends InnerController {

    @ViewNode
    private JFXTextField numberField;

    @ViewNode
    private JFXComboBox<DtoDeviceCompany> companyField;

    @ViewNode
    private JFXComboBox<DtoDeviceBrand> brandField;

    @ViewNode
    private JFXComboBox<DtoDeviceModel> modelField;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("submit")
    private JFXButton submitButton;

    @PostConstruct
    private void init() {

        brandField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            modelField.setDisable(newValue == null);
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addSupplierInExecutor(() -> Api.getInstance().device().getModels())
                    .addConsumerInPlatformThread(
                            dtoDeviceModels -> modelField.getItems().setAll(
                                    dtoDeviceModels.stream().filter(model -> model.getBrand().equals(newValue)).collect(Collectors.toList())
                            )
                    )
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().device().getCompanies())
                .addConsumerInPlatformThread(dtoDeviceCompanies -> companyField.getItems().setAll(dtoDeviceCompanies))
                .addSupplierInExecutor(() -> Api.getInstance().device().getBrands())
                .addConsumerInPlatformThread(dtoDeviceBrands -> brandField.getItems().setAll(dtoDeviceBrands))
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("submit")
    private void submit() {
        if (numberField.getText().isEmpty() || companyField.getValue() == null || brandField.getValue() == null || modelField.getValue() == null) {
            showMessage("Completa todos los campos");
        } else {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().app().linkDevice(
                            numberField.getText(),
                            companyField.getSelectionModel().getSelectedItem().getId(),
                            modelField.getSelectionModel().getSelectedItem().getId()
                    ))
                    .addRunnableInPlatformThread(this::navigateBack)
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        }
    }

}

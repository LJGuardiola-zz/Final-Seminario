package gui.controllers.space.open;

import api.Api;
import api.dto.DtoCampaign;
import api.dto.DtoCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/space/open/form.fxml", title = "Crear un espacio abierto")
public class OpenSpaceCreateController extends InnerController {

    @ViewNode
    private JFXTextField nameField;

    @ViewNode
    private JFXCheckBox availableField;

    @ViewNode
    private JFXTextField latitudeField;

    @ViewNode
    private JFXTextField longitudeField;

    @ViewNode
    private JFXTextField capacityField;

    @ViewNode
    private JFXTextField radiusField;

    @ViewNode
    private JFXComboBox<DtoCategory> categoryField;

    @ViewNode
    private JFXComboBox<DtoCampaign> entryCampaignField;

    @ViewNode
    private JFXComboBox<DtoCampaign> exitCampaignField;

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
                .addSupplierInExecutor(() -> Api.getInstance().category().getAll())
                .addConsumerInPlatformThread(categories -> categoryField.getItems().addAll(categories))
                .addSupplierInExecutor(() -> Api.getInstance().campaign().getAll())
                .addConsumerInPlatformThread(campaigns -> {
                    entryCampaignField.getItems().addAll(campaigns);
                    exitCampaignField.getItems().addAll(campaigns);
                })
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("submit")
    private void submit() {
        if (nameField.getText().isEmpty() || latitudeField.getText().isEmpty() || longitudeField.getText().isEmpty() ||
            capacityField.getText().isEmpty() || radiusField.getText().isEmpty() || categoryField.getSelectionModel().getSelectedItem() == null ||
            entryCampaignField.getSelectionModel().getSelectedItem() == null || exitCampaignField.getSelectionModel().getSelectedItem() == null)
            showMessage("Completa todos los campos");
        else {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().space().create(
                            nameField.getText(),
                            availableField.isSelected(),
                            latitudeField.getText(),
                            longitudeField.getText(),
                            radiusField.getText(),
                            capacityField.getText(),
                            categoryField.getSelectionModel().getSelectedItem().getId(),
                            entryCampaignField.getSelectionModel().getSelectedItem().getId(),
                            exitCampaignField.getSelectionModel().getSelectedItem().getId()
                    ))
                    .addRunnableInPlatformThread(this::navigateBack)
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        }
    }

}

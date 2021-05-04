package gui.controllers.space.open;

import api.Api;
import api.dto.DtoCampaign;
import api.dto.DtoCategory;
import api.dto.DtoOpenSpace;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.space.open.model.OpenSpaceDataModel;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/space/open/form.fxml", title = "Crear un espacio abierto")
public class OpenSpaceEditController extends InnerController {

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

    private OpenSpaceDataModel dataModel;

    @PostConstruct
    private void init() {

        dataModel = ApplicationContext.getInstance().getRegisteredObject(OpenSpaceDataModel.class);

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().category().getAll())
                .addConsumerInPlatformThread(categories -> categoryField.getItems().addAll(categories))
                .addSupplierInExecutor(() -> Api.getInstance().campaign().getAll())
                .addConsumerInPlatformThread(campaigns -> {
                    entryCampaignField.getItems().addAll(campaigns);
                    exitCampaignField.getItems().addAll(campaigns);
                })
                .addSupplierInExecutor(() -> Api.getInstance().space().searchOpen(dataModel.getSelected().getId()))
                .addConsumerInPlatformThread(this::loadForm)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    private void loadForm(DtoOpenSpace dtoOpenSpace) {
        nameField.setText(dtoOpenSpace.getName());
        availableField.setSelected(dtoOpenSpace.isAvailable());
        longitudeField.setText(String.valueOf(dtoOpenSpace.getLongitude()));
        latitudeField.setText(String.valueOf(dtoOpenSpace.getLatitude()));
        radiusField.setText(String.valueOf(dtoOpenSpace.getRadius()));
        capacityField.setText(String.valueOf(dtoOpenSpace.getCapacity()));
        categoryField.getSelectionModel().select(dtoOpenSpace.getCategory());
        entryCampaignField.getSelectionModel().select(dtoOpenSpace.getEntryCampaign());
        exitCampaignField.getSelectionModel().select(dtoOpenSpace.getExitCampaign());
    }

    @ActionMethod("submit")
    private void submit() {
        if (nameField.getText().isEmpty() || latitudeField.getText().isEmpty() || longitudeField.getText().isEmpty() ||
                capacityField.getText().isEmpty() || radiusField.getText().isEmpty() || categoryField.getSelectionModel().getSelectedItem() == null ||
                entryCampaignField.getSelectionModel().getSelectedItem() == null || exitCampaignField.getSelectionModel().getSelectedItem() == null) {
            showMessage("Completa todos los campos");
        } else {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().space().update(
                            dataModel.getSelected().getId(),
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

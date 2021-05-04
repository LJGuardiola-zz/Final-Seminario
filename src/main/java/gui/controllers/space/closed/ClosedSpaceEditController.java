package gui.controllers.space.closed;

import api.Api;
import api.dto.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.person.PersonCreateController;
import gui.controllers.space.closed.model.ClosedSpaceDataModel;
import gui.controllers.space.closed.model.ClosedSpaceSave;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.util.VetoException;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/space/closed/form.fxml", title = "Crear un espacio cerrado")
public class ClosedSpaceEditController extends InnerController {

    @ViewNode
    private JFXTextField nameField;

    @ViewNode
    private JFXCheckBox availableField;

    @ViewNode
    private JFXTextField streetField;

    @ViewNode
    private JFXComboBox<DtoCity> cityField;

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
    private JFXComboBox<DtoPerson> responsibleField;

    @ViewNode
    @ActionTrigger("addResponsible")
    private JFXButton addButton;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("submit")
    private JFXButton submitButton;

    private ClosedSpaceDataModel dataModel;

    private final ClosedSpaceSave dataSave = ClosedSpaceSave.getInstance();

    @PostConstruct
    private void init() {

        dataModel = ApplicationContext.getInstance().getRegisteredObject(ClosedSpaceDataModel.class);

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().space().getAllCities())
                .addConsumerInExecutor(cities -> cityField.getItems().addAll(cities))
                .addSupplierInExecutor(() -> Api.getInstance().category().getAll())
                .addConsumerInPlatformThread(categories -> categoryField.getItems().addAll(categories))
                .addSupplierInExecutor(() -> Api.getInstance().campaign().getAll())
                .addConsumerInPlatformThread(campaigns -> {
                    entryCampaignField.getItems().addAll(campaigns);
                    exitCampaignField.getItems().addAll(campaigns);
                })
                .addSupplierInExecutor(() -> Api.getInstance().person().getAll())
                .addConsumerInPlatformThread(persons -> responsibleField.getItems().addAll(persons))
                .addSupplierInExecutor(() -> Api.getInstance().space().searchClosed(dataModel.getSelected().getId()))
                .addConsumerInPlatformThread(this::loadData)
                .addRunnableInPlatformThread(this::loadDataSave)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    private void loadData(DtoClosedSpace dtoClosedSpace) {
        nameField.setText(dtoClosedSpace.getName());
        availableField.setSelected(dtoClosedSpace.isAvailable());
        streetField.setText(dtoClosedSpace.getStreet());
        cityField.getSelectionModel().select(dtoClosedSpace.getCity());
        longitudeField.setText(String.valueOf(dtoClosedSpace.getLongitude()));
        latitudeField.setText(String.valueOf(dtoClosedSpace.getLatitude()));
        radiusField.setText(String.valueOf(dtoClosedSpace.getRadius()));
        capacityField.setText(String.valueOf(dtoClosedSpace.getCapacity()));
        categoryField.getSelectionModel().select(dtoClosedSpace.getCategory());
        entryCampaignField.getSelectionModel().select(dtoClosedSpace.getEntryCampaign());
        exitCampaignField.getSelectionModel().select(dtoClosedSpace.getExitCampaign());
        responsibleField.getSelectionModel().select(dtoClosedSpace.getResponsible());
    }

    private void loadDataSave() {
        if (dataSave.isAssign()) {
            dataSave.setAssign(false);
            nameField.setText(dataSave.getName());
            availableField.setSelected(dataSave.isAvailable());
            streetField.setText(dataSave.getStreet());
            cityField.getSelectionModel().select(dataSave.getCity());
            latitudeField.setText(dataSave.getLatitude());
            longitudeField.setText(dataSave.getLongitude());
            radiusField.setText(dataSave.getRadius());
            capacityField.setText(dataSave.getCapacity());
            categoryField.getSelectionModel().select(dataSave.getCategory());
            entryCampaignField.getSelectionModel().select(dataSave.getEntryCampaign());
            exitCampaignField.getSelectionModel().select(dataSave.getExitCampaign());
            responsibleField.getSelectionModel().select(dataSave.getResponsible());
        }
    }

    @ActionMethod("addResponsible")
    private void addResponsible() {
        try {
            dataSave.setAssign(true);
            dataSave.setName(nameField.getText());
            dataSave.setAvailable(availableField.isSelected());
            dataSave.setStreet(streetField.getText());
            dataSave.setCity(cityField.getSelectionModel().getSelectedItem());
            dataSave.setLatitude(latitudeField.getText());
            dataSave.setLongitude(longitudeField.getText());
            dataSave.setRadius(radiusField.getText());
            dataSave.setCapacity(capacityField.getText());
            dataSave.setCategory(categoryField.getSelectionModel().getSelectedItem());
            dataSave.setEntryCampaign(entryCampaignField.getSelectionModel().getSelectedItem());
            dataSave.setExitCampaign(exitCampaignField.getSelectionModel().getSelectedItem());
            dataSave.setResponsible(responsibleField.getSelectionModel().getSelectedItem());
            flowActionHandler.navigate(PersonCreateController.class);
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }

    @ActionMethod("submit")
    private void submit() {
        if (nameField.getText().isEmpty() || streetField.getText().isEmpty() || cityField.getSelectionModel().getSelectedItem() == null ||
                latitudeField.getText().isEmpty() || longitudeField.getText().isEmpty() || capacityField.getText().isEmpty() ||
                radiusField.getText().isEmpty() || categoryField.getSelectionModel().getSelectedItem() == null ||
                entryCampaignField.getSelectionModel().getSelectedItem() == null || exitCampaignField.getSelectionModel().getSelectedItem() == null ||
                responsibleField.getSelectionModel().getSelectedItem() == null) {
            showMessage("Completa todos los campos");
        } else {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().space().update(
                            dataModel.getSelected().getId(),
                            nameField.getText(),
                            availableField.isSelected(),
                            streetField.getText(),
                            cityField.getSelectionModel().getSelectedItem().getId(),
                            latitudeField.getText(),
                            longitudeField.getText(),
                            radiusField.getText(),
                            capacityField.getText(),
                            categoryField.getSelectionModel().getSelectedItem().getId(),
                            entryCampaignField.getSelectionModel().getSelectedItem().getId(),
                            exitCampaignField.getSelectionModel().getSelectedItem().getId(),
                            responsibleField.getSelectionModel().getSelectedItem().getId()
                    ))
                    .addRunnableInPlatformThread(this::navigateBack)
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        }
    }
    
}

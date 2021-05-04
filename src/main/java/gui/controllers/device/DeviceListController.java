package gui.controllers.device;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.device.model.Device;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@ViewController(value = "/gui/views/device/list.fxml", title = "Historial de dispositivos")
public class DeviceListController extends InnerController {

    @ViewNode
    @ActionTrigger("newLink")
    private JFXButton newButton;

    @ViewNode
    private TableView<Device> table;

    @FXML
    private TableColumn<Device, Integer> numberColumn;

    @FXML
    private TableColumn<Device, String> companyColumn;

    @FXML
    private TableColumn<Device, String> modelColumn;

    @FXML
    private TableColumn<Device, String> startColumn;

    @FXML
    private TableColumn<Device, String> endColumn;

    @PostConstruct
    private void init() {

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().app().getDevices())
                .addConsumerInPlatformThread(
                        devices -> {
                            table.getItems().setAll(devices.stream().map(Device::new).collect(Collectors.toList()));
                            table.getSortOrder().clear();
                            table.getSortOrder().add(endColumn);
                            table.sort();
                        })
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("newLink")
    private void newLink() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().app().getCurrentDevice())
                .addConsumerInPlatformThread(dtoDevice -> confirm(
                        "¿Seguro desea desvincular el dispositivo " + dtoDevice.getModel().getName() +
                                " - " + dtoDevice.getModel().getBrand().getName() + " y vincular uno nuevo?",
                        () -> navigateTo(DeviceCreateController.class)
                ))
                .onException(t -> navigateTo(DeviceCreateController.class))
                .withFinal(this::endLoading)
                .run();
    }

}

package gui.controllers.warning;

import api.Api;
import gui.controllers.base.InnerController;
import gui.controllers.warning.model.Warning;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.core.concurrent.ProcessChain;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@ViewController(value = "/gui/views/warning/list.fxml", title = "Espacios que superaron los limites establecidos")
public class WarningListController extends InnerController {

    @ViewNode
    private TableView<Warning> table;

    @FXML
    private TableColumn<Warning, String> spaceNameColumn;

    @FXML
    private TableColumn<Warning, String> spaceTypeColumn;

    @FXML
    private TableColumn<Warning, Integer> capacityColumn;

    @FXML
    private TableColumn<Warning, Integer> registeredColumn;

    @FXML
    private TableColumn<Warning, Integer> surpassedColumn;

    @PostConstruct
    private void init() {

        spaceNameColumn.setCellValueFactory(new PropertyValueFactory<>("spaceName"));
        spaceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("spaceType"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        registeredColumn.setCellValueFactory(new PropertyValueFactory<>("registeredDevices"));
        surpassedColumn.setCellValueFactory(new PropertyValueFactory<>("surpassedBy"));

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().space().getSpacesWarnings())
                .addConsumerInPlatformThread(warnings -> table.setItems(
                        warnings.stream().map(Warning::new).collect(Collectors.toCollection(FXCollections::observableArrayList))
                ))
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

}

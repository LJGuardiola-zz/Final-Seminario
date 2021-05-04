package gui.controllers.register;

import api.Api;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import gui.controllers.base.InnerController;
import gui.controllers.register.model.SpaceSummary;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.core.concurrent.ProcessChain;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

@ViewController(value = "/gui/views/gob/devices.fxml", title = "Registro de dispositivos")
public class RegisteredDevicesController extends InnerController {

    @ViewNode
    private JFXDatePicker dateField;

    @ViewNode
    private JFXTimePicker timeField;

    @ViewNode
    @ActionTrigger("apply")
    private JFXButton applyButton;

    @ViewNode
    private TableView<SpaceSummary> table;

    @FXML
    private TableColumn<SpaceSummary, String> spaceColumn;

    @FXML
    private TableColumn<SpaceSummary, Integer> capacityColumn;

    @FXML
    private TableColumn<SpaceSummary, Integer> devicesColumn;

    @FXML
    private TableColumn<SpaceSummary, Integer> amountColumn;

    @FXML
    private TableColumn<SpaceSummary, Boolean> superedColumn;

    @PostConstruct
    private void init() {

        spaceColumn.setCellValueFactory(new PropertyValueFactory<>("space"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        devicesColumn.setCellValueFactory(new PropertyValueFactory<>("devices"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        superedColumn.setCellValueFactory(new PropertyValueFactory<>("supered"));
        superedColumn.setCellFactory(param -> new CheckBoxTableCell<>());

        dateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate date = dateField.getValue();
            LocalTime time = timeField.getValue();
            applyButton.setDisable(
                    date == null || time == null || LocalDateTime.of(date, time).isAfter(LocalDateTime.now())
            );
        });

        timeField.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate date = dateField.getValue();
            LocalTime time = timeField.getValue();
            applyButton.setDisable(
                    date == null || time == null || LocalDateTime.of(date, time).isAfter(LocalDateTime.now())
            );
        });

        timeField.set24HourView(true);

        dateField.setValue(LocalDate.now());
        timeField.setValue(LocalTime.now());
        apply();

    }

    @ActionMethod("apply")
    private void apply() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().space().getSummaries(
                        LocalDateTime.of(dateField.getValue(), timeField.getValue())
                ))
                .addConsumerInPlatformThread(summaries -> table.setItems(
                        summaries.stream().map(SpaceSummary::new).collect(Collectors.toCollection(FXCollections::observableArrayList))
                ))
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

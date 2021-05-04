package gui.controllers.today;

import api.Api;
import gui.controllers.base.InnerController;
import gui.controllers.today.model.Summary;
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

@ViewController(value = "/gui/views/today/list.fxml", title = "Resumen de hoy por categorías")
public class TodayController extends InnerController {

    @ViewNode
    private TableView<Summary> table;

    @FXML
    private TableColumn<Summary, String> categoryColumn;

    @FXML
    private TableColumn<Summary, Long> devicesColumn;

    @FXML
    private TableColumn<Summary, Long> amountColumn;

    @PostConstruct
    private void init() {

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        devicesColumn.setCellValueFactory(new PropertyValueFactory<>("devices"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().space().getSummariesForCategories())
                .addConsumerInPlatformThread(summaries -> table.setItems(
                        summaries.stream().map(Summary::new).collect(Collectors.toCollection(FXCollections::observableArrayList))
                ))
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

}

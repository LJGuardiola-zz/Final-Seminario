package gui.controllers.notifications;

import api.Api;
import api.dto.DtoNotification;
import gui.controllers.base.InnerController;
import gui.controllers.notifications.model.Notification;
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
import java.util.List;
import java.util.stream.Collectors;

@ViewController(value = "/gui/views/notification/listing.fxml", title = "Listado de notificaciones")
public class NotificationsListController extends InnerController {

    @ViewNode
    private TableView<Notification> table;

    @FXML
    private TableColumn<Notification, String> deviceColumn;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, String> dateColumn;

    @PostConstruct
    private void init() {

        deviceColumn.setCellValueFactory(new PropertyValueFactory<>("device"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().app().getNotifications())
                .addConsumerInPlatformThread(this::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    private void load(List<DtoNotification> dtoNotifications) {
        table.setItems(
                dtoNotifications.stream().map(Notification::new)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
        table.getSortOrder().clear();
        table.getSortOrder().add(dateColumn);
        table.sort();
    }

}

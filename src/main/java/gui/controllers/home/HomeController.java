package gui.controllers.home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import gui.controllers.base.InnerController;
import gui.controllers.campaign.CampaignListController;
import gui.controllers.category.CategoryListController;
import gui.controllers.citizen.ImHereController;
import gui.controllers.citizen.MyDataController;
import gui.controllers.device.DeviceListController;
import gui.controllers.global.Session;
import gui.controllers.notifications.NotificationsListController;
import gui.controllers.person.PersonListController;
import gui.controllers.register.RegisteredDevicesController;
import gui.controllers.role.RoleListController;
import gui.controllers.space.closed.ClosedSpaceListController;
import gui.controllers.space.open.OpenSpaceListController;
import gui.controllers.today.TodayController;
import gui.controllers.user.UserListController;
import gui.controllers.warning.WarningListController;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.annotation.PostConstruct;

import static org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*;

@ViewController(value = "/gui/views/home/home.fxml", title = "Inicio")
public class HomeController extends InnerController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXMasonryPane masonryPane;

    private final Session session = Session.getInstance();

    @PostConstruct
    public void init() {

        ObservableList<Node> children = masonryPane.getChildren();

        if (session.hasPermission("role_read"))
            children.add(getButton("Roles", USER_COG, RoleListController.class));

        if (session.hasPermission("user_read"))
            children.add(getButton("Usuarios", USERS, UserListController.class));

        if (session.hasPermission("person_read"))
            children.add(getButton("Personas", USER_FRIENDS, PersonListController.class));

        if (session.hasPermission("category_read"))
            children.add(getButton("Categorías", TAG, CategoryListController.class));

        if (session.hasPermission("campaign_read"))
            children.add(getButton("Campañas", STICKY_NOTE, CampaignListController.class));

        if (session.isGob())
            children.add(getButton("Resumen de hoy", CALENDAR_DAY, TodayController.class));

        if (session.isGob())
            children.add(getButton("Registro", CALENDAR, RegisteredDevicesController.class));

        if (session.isGob())
            children.add(getButton("Alertas", EXCLAMATION, WarningListController.class));

        if (session.hasPermission("space_read"))
            children.add(getButton("Espacios Cerrados", BUILDING, ClosedSpaceListController.class));

        if (session.hasPermission("space_read"))
            children.add(getButton("Espacios Abiertos", TREE, OpenSpaceListController.class));

        if (session.isCitizen())
            children.add(getButton("Estoy aquí", MAP_MARKED_ALT, ImHereController.class));

        if (session.isCitizen())
            children.add(getButton("Notificaciones", BELL, NotificationsListController.class));

        if (session.isCitizen())
            children.add(getButton("Dispositivos", MOBILE, DeviceListController.class));

        if (session.isCitizen())
            children.add(getButton("Mis datos", DIGITAL_TACHOGRAPH, MyDataController.class));

        Platform.runLater(() -> scrollPane.requestLayout());
        JFXScrollPane.smoothScrolling(scrollPane);

    }

    private JFXButton getButton(String text, Ikon ikon, Class<?> link) {
        JFXButton child = new JFXButton(text, new FontIcon(ikon));
        child.setOnAction(event -> navigateTo(link));
        child.getStyleClass().add("home-button");
        return child;
    }

}

package gui.util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import io.datafx.controller.context.ApplicationContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertFactory {

    public static JFXAlert<?> alert(String title, String message, EventHandler<ActionEvent> eventHandler) {

        Stage stage = (Stage) ApplicationContext.getInstance().getRegisteredObject("stage");

        JFXAlert<Void> alert = new JFXAlert<>(stage);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();

        layout.setHeading(new Label(title));
        layout.setBody(new Label(message));

        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(eventHandler);

        layout.setActions(ok);

        alert.setContent(layout);

        return alert;

    }

}

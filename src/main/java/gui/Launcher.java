package gui;

import api.Api;
import api.exceptions.ApiException;
import com.jfoenix.controls.JFXDecorator;
import gui.controllers.WizardController;
import gui.util.AlertFactory;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class Launcher extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        DefaultFlowContainer container = new DefaultFlowContainer();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.register("stage", stage);

        JFXDecorator decorator = new JFXDecorator(stage, container.getView(), false, true, true);
        decorator.setGraphic(new FontIcon());
        decorator.setCustomMaximize(true);

        new Flow(WizardController.class).start(container);

        Scene scene = new Scene(decorator, 835, 700);
        scene.getStylesheets().add(
                getClass().getResource("/gui/styles/light.css").toExternalForm()
        );

        stage.setTitle("Universidad Nacional de Rio Negro");
        stage.getIcons().add(new Image("/gui/images/icon.png"));
        stage.setMinHeight(700);
        stage.setMinWidth(835);
        stage.setScene(scene);
        stage.show();

        try {
            Api.getInstance().app().testConnection();
        } catch (ApiException e) {
            AlertFactory.alert(
                    "Error", e.getMessage(), event -> Platform.exit()
            ).show();
        }

    }

}

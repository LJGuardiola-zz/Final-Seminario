package gui.controllers.base;

import com.jfoenix.controls.*;
import gui.util.interfaces.Function;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

public abstract class InnerController {

    @FXML
    protected StackPane root;

    private JFXSnackbar bar;

    @ActionHandler
    protected FlowActionHandler flowActionHandler;

    @FXMLViewFlowContext
    protected ViewFlowContext flowContext;

    protected void startLoading() {
        try {
            flowActionHandler.handle("show_spinner");
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }

    protected void endLoading() {
        try {
            flowActionHandler.handle("hide_spinner");
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }

    protected void showMessage(Throwable throwable) {
        showMessage(throwable.getMessage());
    }

    protected void showMessage(String message) {
        if (bar == null)
            bar = new JFXSnackbar(root);
        bar.enqueue(
                new JFXSnackbar.SnackbarEvent(
                        new JFXSnackbarLayout(message)
                )
        );
    }

    protected void navigateBack() {
        try {
            flowActionHandler.navigateBack();
        } catch (FlowException | VetoException e) {
            e.printStackTrace();
        }
    }

    protected void navigateTo(Class<?> controller) {
        try {
            flowActionHandler.navigate(controller);
        } catch (FlowException | VetoException e) {
            e.printStackTrace();
        }
    }

    protected void confirm(String message, Function function) {

        JFXDialog dialog = new JFXDialog();

        JFXDialogLayout layout = new JFXDialogLayout();

        layout.setHeading(new Label("Confirmación"));
        layout.setBody(new Label(message));

        JFXButton yes = new JFXButton("Si");
        yes.getStyleClass().addAll("btn", "btn-orange");
        yes.setOnAction(e -> {
            dialog.close();
            function.apply();
        });

        JFXButton no = new JFXButton("No");
        no.getStyleClass().addAll("btn", "btn-gray");
        no.setOnAction(e -> dialog.close());

        layout.setActions(yes, no);
        dialog.setContent(layout);
        dialog.show(root);

    }

    public boolean confirmBack() {

        if (!backRequiredConfirm())
            return true;

        JFXAlert<Boolean> alert = new JFXAlert<>(root.getScene().getWindow());

        alert.setOverlayClose(false);
        alert.initModality(Modality.APPLICATION_MODAL);

        JFXDialogLayout layout = new JFXDialogLayout();

        layout.setHeading(new Label("Confirmación"));
        layout.setBody(new Label("¿Seguro?, se perderán todos los cambios realizados."));

        JFXButton no = new JFXButton("No");
        no.setOnAction(event -> alert.setResult(false));

        JFXButton yes = new JFXButton("Si");
        yes.setOnAction(event -> alert.setResult(true));

        layout.setActions(no, yes);

        alert.setContent(layout);

        return alert.showAndWait().orElse(false);

    }

    protected boolean backRequiredConfirm() {
        return false;
    }

}

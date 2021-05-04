package gui.util;

import api.exceptions.InvalidDataException;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import io.datafx.controller.context.ApplicationContext;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class UiExceptionHandler {

    private static JFXSnackbar snackbar;

    public static void handle(Throwable throwable) {
        if (throwable instanceof InvocationTargetException)
            throwable = ((InvocationTargetException) throwable).getTargetException();

        List<String> messages = new ArrayList<>();

        if (throwable instanceof InvalidDataException) {
            messages.addAll(((InvalidDataException) throwable).getErros());
        } else {
            messages.add(throwable.getMessage());
        }

        if (snackbar == null) {
            snackbar = new JFXSnackbar(
                    (Pane) ApplicationContext.getInstance().getRegisteredObject("root")
            );
        }
        messages.forEach(message -> snackbar.enqueue(
                new JFXSnackbar.SnackbarEvent(
                        new JFXSnackbarLayout(message)
                )
        ));
    }

}

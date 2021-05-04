package gui.controllers.category;

import api.Api;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

@ViewController(value = "/gui/views/category/form.fxml", title = "Crear una categoría")
public class CategoryCreateController extends InnerController {

    @ViewNode
    private JFXTextField idField;

    @ViewNode
    private JFXTextField nameField;

    @ViewNode
    private JFXTextField descriptionField;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("submit")
    private JFXButton submitButton;

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().category().create(
                        nameField.getText(),
                        descriptionField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

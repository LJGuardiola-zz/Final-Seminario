package gui.controllers.campaign;

import api.Api;
import com.jfoenix.controls.*;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

@ViewController(value = "/gui/views/campaign/form.fxml", title = "Crear una campaña")
public class CampaignCreateController extends InnerController {

    @ViewNode
    private JFXTextField idField;

    @ViewNode
    private JFXTextArea messageField;

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
                .addRunnableInExecutor(() -> Api.getInstance().campaign().create(
                        messageField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

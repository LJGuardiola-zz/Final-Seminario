package gui.controllers.campaign;

import api.Api;
import api.dto.DtoCampaign;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.campaign.model.CampaignDataModel;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/campaign/form.fxml", title = "Editar una campaña")
public class CampaignEditController extends InnerController {

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

    private CampaignDataModel dataModel;

    private DtoCampaign campaignToEdit;

    @PostConstruct
    private void init() {

        dataModel = ApplicationContext.getInstance().getRegisteredObject(CampaignDataModel.class);

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().campaign().search(dataModel.getSelected().getId()))
                .addConsumerInPlatformThread(this::loadForm)
                .onException(Throwable::printStackTrace)
                .withFinal(this::endLoading)
                .run();

    }

    private void loadForm(DtoCampaign dtoCampaign) {

        campaignToEdit = dtoCampaign;

        idField.setText(String.valueOf(dtoCampaign.getId()));
        messageField.setText(dtoCampaign.getMessage());

    }

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().campaign().update(
                        campaignToEdit.getId(), messageField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

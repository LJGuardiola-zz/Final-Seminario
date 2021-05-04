package gui.controllers.category;

import api.Api;
import api.dto.DtoCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.controllers.base.InnerController;
import gui.controllers.category.model.CategoryDataModel;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/category/form.fxml", title = "Editar una categoría")
public class CategoryEditController extends InnerController {

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

    private CategoryDataModel dataModel;

    private DtoCategory categoryToEdit;

    @PostConstruct
    private void init() {

        dataModel = ApplicationContext.getInstance().getRegisteredObject(CategoryDataModel.class);

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().category().search(dataModel.getSelected().getId()))
                .addConsumerInPlatformThread(this::loadForm)
                .onException(Throwable::printStackTrace)
                .withFinal(this::endLoading)
                .run();
    }

    private void loadForm(DtoCategory dtoCategory) {
        categoryToEdit = dtoCategory;
        idField.setText(String.valueOf(dtoCategory.getId()));
        nameField.setText(dtoCategory.getName());
        descriptionField.setText(dtoCategory.getDescription());
    }

    @ActionMethod("submit")
    private void submit() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(() -> Api.getInstance().category().update(
                        categoryToEdit.getId(), nameField.getText(), descriptionField.getText()
                ))
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

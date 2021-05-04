package gui.controllers.role;

import api.Api;
import api.dto.DtoRole;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gui.controllers.base.InnerController;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@ViewController(value = "/gui/views/role/form.fxml", title = "Crear un rol")
public class RoleCreateController extends InnerController {

    @ViewNode
    private JFXTextField nameField;

    @ViewNode
    private JFXCheckBox enabledField;

    @ViewNode
    private VBox topBox, bottomBox;

    @ViewNode
    private JFXToggleButton rolesAll, rolesRead, rolesCreate, rolesUpdate, rolesDelete,
            usersAll, usersRead, usersCreate, usersUpdate, usersDelete,
            personsAll, personsRead, personsCreate, personsUpdate, personsDelete,
            categoriesAll, categoriesRead, categoriesCreate, categoriesUpdate, categoriesDelete,
            campaignsAll, campaignsRead, campaignsCreate, campaignsUpdate, campaignsDelete,
            spacesAll, spacesRead, spacesCreate, spacesUpdate, spacesDelete;

    @ViewNode
    @ActionTrigger("next")
    private JFXButton nextButton;

    @ViewNode
    @BackAction
    private JFXButton cancelButton;

    @ViewNode
    @ActionTrigger("end")
    private JFXButton endButton;

    private DtoRole role;

    private final Set<String> permsSelected = new HashSet<>();

    @PostConstruct
    private void init() {
        addListeners(rolesAll, rolesRead, rolesCreate, rolesUpdate, rolesDelete);
        addListeners(usersAll, usersRead, usersCreate, usersUpdate, usersDelete);
        addListeners(personsAll, personsRead, personsCreate, personsUpdate, personsDelete);
        addListeners(categoriesAll, categoriesRead, categoriesCreate, categoriesUpdate, categoriesDelete);
        addListeners(campaignsAll, campaignsRead, campaignsCreate, campaignsUpdate, campaignsDelete);
        addListeners(spacesAll, spacesRead, spacesCreate, spacesUpdate, spacesDelete);
    }

    private void addListeners(JFXToggleButton... listPerms) {
        listPerms[0].addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            for (int i = 1; i < listPerms.length; i++) {
                listPerms[i].setSelected(listPerms[0].isSelected());
            }
        });
        for (int i = 1; i < listPerms.length; i++) {
            JFXToggleButton btn = listPerms[i];
            btn.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) permsSelected.add(btn.getUserData().toString());
                else permsSelected.remove(btn.getUserData().toString());
            });
            btn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                boolean allSelected = true;
                for (int j = 1; j < listPerms.length; j++) {
                    if (!listPerms[j].isSelected()) {
                        allSelected = false;
                        break;
                    }
                }
                listPerms[0].setSelected(allSelected);
            });
        }
    }

    @ActionMethod("next")
    protected void next() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(
                        () -> Api.getInstance().role().create(nameField.getText(), enabledField.isSelected())
                )
                .addConsumerInPlatformThread((role) -> {
                    this.role = role;
                    topBox.setDisable(true);
                    bottomBox.setDisable(false);
                })
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

    @ActionMethod("end")
    private void end() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addRunnableInExecutor(
                        () -> Api.getInstance().role().assignPermission(role.getID(), permsSelected)
                )
                .addRunnableInPlatformThread(this::navigateBack)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();
    }

}

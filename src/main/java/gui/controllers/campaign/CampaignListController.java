package gui.controllers.campaign;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.campaign.model.Campaign;
import gui.controllers.campaign.model.CampaignDataModel;
import gui.controllers.global.Session;
import gui.util.UiExceptionHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/campaign/list.fxml", title = "Listado de campañas")
public class CampaignListController extends InnerController {

    @ViewNode
    private HBox btnContainer;

    @ViewNode
    @LinkAction(CampaignCreateController.class)
    private JFXButton newButton;

    @ViewNode
    @LinkAction(CampaignEditController.class)
    private JFXButton editButton;

    @ViewNode
    @ActionTrigger("delete")
    private JFXButton deleteButton;

    @ViewNode
    private TableView<Campaign> table;

    @FXML
    private TableColumn<Campaign, Integer> idColumn;

    @FXML
    private TableColumn<Campaign, String> messageColumn;

    private CampaignDataModel dataModel;

    private final Session session = Session.getInstance();

    @PostConstruct
    private void init() {

        ApplicationContext.getInstance().register(dataModel = new CampaignDataModel(), CampaignDataModel.class);

        if (!session.hasPermission("campaign_update")) {
            btnContainer.getChildren().remove(editButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                editButton.setDisable(newValue == null);
            });
        }

        if (!session.hasPermission("campaign_delete")) {
            btnContainer.getChildren().remove(deleteButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                deleteButton.setDisable(newValue == null);
            });
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        table.itemsProperty().bindBidirectional(dataModel.getData());
        table.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> dataModel.setSelected(newValue)
        );

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().campaign().getAll())
                .addConsumerInPlatformThread(dataModel::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("delete")
    private void delete() {
        Campaign campaign = dataModel.getSelected();
        confirm("¿Seguro desea eliminar la campaña " + campaign.getId() + "?", () -> {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().campaign().delete(campaign.getId()))
                    .addRunnableInPlatformThread(() -> dataModel.delete(campaign))
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });
    }

}

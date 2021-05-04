package gui.controllers.person;

import api.Api;
import com.jfoenix.controls.JFXButton;
import gui.controllers.base.InnerController;
import gui.controllers.global.Session;
import gui.controllers.person.model.Person;
import gui.controllers.person.model.PersonDataModel;
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

@ViewController(value = "/gui/views/person/listing.fxml", title = "Listado de personas")
public class PersonListController extends InnerController {

    @ViewNode
    private HBox btnContainer;

    @ViewNode
    @LinkAction(PersonCreateController.class)
    private JFXButton newButton;

    @ViewNode
    @ActionTrigger("delete")
    private JFXButton deleteButton;

    @ViewNode
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, Integer> idColumn;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> cuiColumn;

    @FXML
    private TableColumn<Person, String> typeColumn;

    private PersonDataModel dataModel;

    private final Session session = Session.getInstance();

    @PostConstruct
    private void init() {

        ApplicationContext.getInstance().register(dataModel = new PersonDataModel(), PersonDataModel.class);

        if (!session.hasPermission("person_delete")) {
            btnContainer.getChildren().remove(deleteButton);
        } else {
            table.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                deleteButton.setDisable(newValue == null);
            });
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cuiColumn.setCellValueFactory(new PropertyValueFactory<>("cui"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        table.itemsProperty().bindBidirectional(dataModel.getData());
        table.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> dataModel.setSelected(newValue)
        );

        ProcessChain.create()
                .addRunnableInPlatformThread(this::startLoading)
                .addSupplierInExecutor(() -> Api.getInstance().person().getAll())
                .addConsumerInPlatformThread(dataModel::load)
                .onException(UiExceptionHandler::handle)
                .withFinal(this::endLoading)
                .run();

    }

    @ActionMethod("delete")
    private void delete() {
        Person person = dataModel.getSelected();
        confirm("¿Seguro desea eliminar a \"" + person.getName() + "\"", () -> {
            ProcessChain.create()
                    .addRunnableInPlatformThread(this::startLoading)
                    .addRunnableInExecutor(() -> Api.getInstance().person().delete(person.getId()))
                    .addRunnableInPlatformThread(() -> dataModel.delete(person))
                    .onException(UiExceptionHandler::handle)
                    .withFinal(this::endLoading)
                    .run();
        });
    }

}

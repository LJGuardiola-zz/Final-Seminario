package gui.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import gui.controllers.authentication.LoginController;
import gui.controllers.base.InnerController;
import gui.controllers.home.HomeController;
import gui.controllers.global.Session;
import gui.util.extensions.ExtendedAnimatedFlowContainer;
import gui.util.extensions.ExtendedContainerAnimations;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.util.VetoException;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

@ViewController(value = "/gui/views/wizard.fxml")
public class WizardController {

    @ViewNode
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    @ActionTrigger("back")
    private JFXButton backButton;

    @FXML
    @ActionTrigger("logout")
    private JFXButton logoutButton;

    @FXML
    private ScrollPane container;

    @FXML
    private StackPane spinner;

    private final Session session = Session.getInstance();

    private FlowHandler innerFlowHandler;

    @PostConstruct
    public void init() throws FlowException {

        innerFlowHandler = getInnerFlow().createHandler();

        logoutButton.visibleProperty().bind(session.isLoggedProperty());

        innerFlowHandler.getCurrentViewProperty().addListener((o) -> {
            updateTitle();
            disableBackButtonOnHome();
        });

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.register("root", root);

        container.setContent(
                innerFlowHandler.start(
                        new ExtendedAnimatedFlowContainer(Duration.millis(320), ExtendedContainerAnimations.SWIPE_RIGHT)
                )
        );

        JFXScrollPane.smoothScrolling(container);

    }

    private void updateTitle() {
        titleLabel.textProperty().bind(innerFlowHandler.getCurrentViewContext().getMetadata().titleProperty());
    }

    private void disableBackButtonOnHome() {
        Object controller = innerFlowHandler.getCurrentViewContext().getController();
        backButton.setDisable(controller instanceof HomeController || controller instanceof LoginController);
    }

    private Flow getInnerFlow() {
        return new Flow(LoginController.class)
                .withGlobalAction("show_spinner", (handler, id) -> showSpinner())
                .withGlobalAction("hide_spinner", (handler, id) -> hideSpinner());
    }

    private void showSpinner() {
        if (!spinner.isVisible()) {
            spinner.setVisible(true);
            FadeIn fadeIn = new FadeIn(spinner);
            fadeIn.play();
        }
    }

    private void hideSpinner() {
        if (spinner.isVisible()) {
            FadeOut fadeOut = new FadeOut(spinner);
            fadeOut.play();
            fadeOut.setOnFinished(
                    e -> spinner.setVisible(false)
            );
        }
    }

    @ActionMethod("back")
    private void back() throws VetoException, FlowException {
        InnerController controller = (InnerController) innerFlowHandler.getCurrentViewContext().getController();
        if (controller.confirmBack()) {
            innerFlowHandler.navigateBack();
        }
    }

    @ActionMethod("logout")
    private void logout() {
        ProcessChain.create()
                .addRunnableInPlatformThread(this::showSpinner)
                .addRunnableInExecutor(session::logout)
                .withFinal(() -> {
                    hideSpinner();
                    try {
                        innerFlowHandler.navigateTo(LoginController.class);
                    } catch (VetoException | FlowException e) {
                        e.printStackTrace();
                    }
                })
                .run();
    }

}

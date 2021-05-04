package gui.util.extensions;

import io.datafx.controller.flow.container.AnimatedFlowContainer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum ExtendedContainerAnimations {

    FADE((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 1.0, Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 0.0, Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    ZOOM_IN((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getPlaceholder().scaleXProperty(), 1, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().scaleYProperty(), 1, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 1.0, Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getPlaceholder().scaleXProperty(), 4, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().scaleYProperty(), 4, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 0, Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    ZOOM_OUT((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getPlaceholder().scaleXProperty(), 1, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().scaleYProperty(), 1, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 1.0, Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getPlaceholder().scaleXProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().scaleYProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().opacityProperty(), 0, Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    SWIPE_LEFT((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getView().translateXProperty(), c.getView().getWidth(), Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateXProperty(), -c.getView().getWidth(), Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getView().translateXProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateXProperty(), -c.getView().getWidth(), Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    SWIPE_RIGHT((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getView().translateXProperty(), -c.getView().getWidth(), Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateXProperty(), c.getView().getWidth(), Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getView().translateXProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateXProperty(), c.getView().getWidth(), Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    SWIPE_TOP((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getView().translateYProperty(), -c.getView().getHeight(), Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateYProperty(), c.getView().getHeight(), Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getView().translateYProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateYProperty(), c.getView().getHeight(), Interpolator.EASE_BOTH)
                            )
                    )
            )
    ),

    SWIPE_BOTTOM((c) ->
            new ArrayList<>(
                    Arrays.asList(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(c.getView().translateYProperty(), c.getView().getHeight(), Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateYProperty(), -c.getView().getHeight(), Interpolator.EASE_BOTH)
                            ),
                            new KeyFrame(
                                    c.getDuration(),
                                    new KeyValue(c.getView().translateYProperty(), 0, Interpolator.EASE_BOTH),
                                    new KeyValue(c.getPlaceholder().translateYProperty(), -c.getView().getHeight(), Interpolator.EASE_BOTH)
                            )
                    )
            )
    );

    private final Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer;

    ExtendedContainerAnimations(Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer) {
        this.animationProducer = animationProducer;
    }

    public Function<AnimatedFlowContainer, List<KeyFrame>> getAnimationProducer() {
        return animationProducer;
    }

}
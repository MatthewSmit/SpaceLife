package com.spacelife.screens;

import com.spacelife.render.Renderer;
import com.spacelife.render.TextRenderer;
import com.spacelife.state.Game;
import com.spacelife.state.SceneAction;
import com.spacelife.state.SceneState;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ActionList {

    private @Nullable SceneAction lastAction;

    @FXML
    public ListView<SceneAction> actionsList;

    private Consumer<SceneAction> actionSelected;

    public void initialize() {
        actionsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onSelectedActionChanged(newValue);
        });

        actionsList.setCellFactory(param -> {
            return new ListCell<SceneAction>() {
                private final Renderer renderer;

                {
                    setOnMouseClicked(event -> {
                        onActionClicked(getItem());
                    });

                    renderer = new TextRenderer();
                }

                @Override
                protected void updateItem(SceneAction item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        renderer.setText(Game.getGame().getCurrentScene(), item.getShortDescription());
                        setText(renderer.getText());
                    }
                }
            };
        });
    }

    private void onSelectedActionChanged(SceneAction newValue) {
        if (actionSelected != null) {
            actionSelected.accept(newValue);
        }
    }

    private void onActionClicked(SceneAction action) {
        if (action != null) {
            if (action.equals(lastAction)) {
                SceneState currentScene = Game.getGame().getCurrentScene();
                action.onSelect(currentScene);
            } else {
                lastAction = action;
            }
        } else {
            lastAction = null;
            actionsList.getSelectionModel().clearSelection();
        }
    }

    public void redraw(SceneState currentScene) {
        actionsList.requestFocus();

        actionsList.getItems().clear();
        actionsList.getItems().addAll(currentScene.getCurrentActions());
        actionsList.getSelectionModel().select(0);
        lastAction = actionsList.getItems().get(0);
    }

    public Consumer<SceneAction> getActionSelected() {
        return actionSelected;
    }

    public void setActionSelected(Consumer<SceneAction> actionSelected) {
        this.actionSelected = actionSelected;
    }

    public void triggerSelection() {
        SceneAction action = actionsList.getSelectionModel().getSelectedItem();
        SceneState currentScene = Game.getGame().getCurrentScene();
        if (action != null) {
            action.onSelect(currentScene);
        }
    }
}

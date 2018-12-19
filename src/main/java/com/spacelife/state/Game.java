package com.spacelife.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Game {
    private static Game game;

    private final List<GameScene> scenes = new ArrayList<>();
    private final EventHandler<SceneState> onSceneChange = new EventHandler<>();
    private final EventHandler<SceneState> onSceneSegmentChange = new EventHandler<>();

    private SceneState currentScene;

    public void addScene(GameScene scene) {
        scenes.add(scene);
    }

    public void changeScene(SceneState newScene) {
        currentScene = newScene;
        onSceneChange.invoke(newScene);
    }

    public void changeSceneSegment() {
        onSceneSegmentChange.invoke(currentScene);
    }

    public SceneState getCurrentScene() {
        Objects.requireNonNull(currentScene);
        return currentScene;
    }

    public void addSceneChangeListener(Consumer<SceneState> listener) {
        onSceneChange.addListener(listener);
    }

    public void addSceneSegmentChangeListener(Consumer<SceneState> listener) {
        onSceneSegmentChange.addListener(listener);
    }

    public static Game getGame() {
        Objects.requireNonNull(game);
        return game;
    }

    public static void setGame(Game game) {
        Game.game = game;
    }
}

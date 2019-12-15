package com.spacelife.screens;

import com.spacelife.state.Game;
import com.spacelife.state.GameScene;
import com.spacelife.state.SceneState;
import com.spacelife.state.script.ScriptGameSceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    public static void main(String[] args) throws IOException {
        Game game = new Game();

        GameScene scene = ScriptGameSceneManager.load("test");

//        GameScene scene = new DemoGameScene();
//        game.addScene(scene);
        SceneState state = new SceneState(game, scene);
//        state.changeNpc("test", new Npc(new Name("John", "Doe"), LocalDate.parse("2000-01-01"), Gender.MALE));
        game.changeScene(state);

        Game.setGame(game);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("SpaceLife");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sceneView.fxml"));

        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

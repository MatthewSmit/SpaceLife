package com.spacelife.state.script;

import com.spacelife.screens.GameApplication;
import com.spacelife.state.GameScene;

import java.io.IOException;

public final class ScriptGameSceneManager {
    private ScriptGameSceneManager() {
    }

    public static GameScene load(String sceneName) throws IOException {
        // TODO: Caching
        return new ScriptGameScene(GameApplication.class.getResource("/scenes/" + sceneName + ".scene"));
    }
}

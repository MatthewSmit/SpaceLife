package com.spacelife.state;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SceneState {
    private final Game game;
    private final GameScene scene;
    private final Map<String, Npc> npcs = new HashMap<>();

    private SceneSegment currentSegment;

    public SceneState(Game game, GameScene scene) {
        this.game = game;
        this.scene = scene;
        currentSegment = scene.getInitialSegment();
    }

    public SceneState(Game game, GameScene scene, Map<String, Npc> npcs) {
        this(game, scene);

        this.npcs.putAll(npcs);
    }

    public void changeNpc(String variable, Npc npc) {
        npcs.put(variable, npc);
    }

    public void changeSegment(SceneSegment newSegment) {
        currentSegment = newSegment;
        game.changeSceneSegment();
    }

    public Collection<SceneAction> getCurrentActions() {
        return currentSegment.getActions();
    }

    public SceneSegment getCurrentSegment() {
        return currentSegment;
    }

    public Npc getNpc(String identifier) {
        return npcs.get(identifier);
    }

    public Map<String, Npc> getNpcs() {
        return Collections.unmodifiableMap(npcs);
    }
}

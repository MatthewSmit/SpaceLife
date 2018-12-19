package com.spacelife.state.demo;

import com.spacelife.state.Game;
import com.spacelife.state.GameScene;
import com.spacelife.state.SceneAction;
import com.spacelife.state.SceneSegment;
import com.spacelife.state.SceneState;

import java.util.Arrays;
import java.util.List;

public class DemoGameScene implements GameScene {
    private final List<SceneSegment> segments;

    public DemoGameScene() {
        SceneSegment intro = new SceneSegment("This is the introduction.\nRandom NPC: {test}");
        SceneSegment state1 = new SceneSegment("This is state 1.");
        SceneSegment state2 = new SceneSegment("This is state 2.");
        SceneSegment state3 = new SceneSegment("This is state 3.");

        intro.addAction(new SceneAction("State 1", "Change to state 1", state -> {
            state.changeSegment(state1);
        }));
        intro.addAction(new SceneAction("State 2", "Change to state 2", state -> {
            state.changeSegment(state2);
        }));

        state1.addAction(new SceneAction("State 2", "Change to state 2", state -> {
            state.changeSegment(state2);
        }));
        state1.addAction(new SceneAction("State 3", "Change to state 3", state -> {
            state.changeSegment(state3);
        }));

        state2.addAction(new SceneAction("State 3", "Change to state 3", state -> {
            state.changeSegment(state3);
        }));

        state3.addAction(new SceneAction("Intro", "Back to the introduction", state -> {
//            Game.getGame().changeScene(new SceneState(Game.getGame(), this, Game.getGame().getCurrentScene().getNpcs()));
            state.changeSegment(intro);
        }));

        segments = Arrays.asList(intro, state1, state2, state3);
    }

    @Override
    public SceneSegment getInitialSegment() {
        return segments.get(0);
    }
}

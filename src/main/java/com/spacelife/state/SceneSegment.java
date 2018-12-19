package com.spacelife.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SceneSegment {

    private final String text;
    private final Collection<SceneAction> actions = new ArrayList<>();

    public SceneSegment(String text) {
        this.text = text;
    }

    public void addAction(SceneAction action) {
        actions.add(action);
    }

    public String getText() {
        return text;
    }

    public Collection<SceneAction> getActions() {
        return Collections.unmodifiableCollection(actions);
    }
}

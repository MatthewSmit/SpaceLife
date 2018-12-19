package com.spacelife.state;

import java.util.function.Consumer;

public class SceneAction {
    private final String shortDescription;
    private final String longDescription;
    private final Consumer<SceneState> onSelect;

    public SceneAction(String shortDescription, String longDescription, Consumer<SceneState> onSelect) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.onSelect = onSelect;
    }

    public void onSelect(SceneState sceneState) {
        onSelect.accept(sceneState);
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }
}

package com.spacelife.state.script;

import com.spacelife.state.GameScene;
import com.spacelife.state.SceneSegment;
import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScriptGameScene implements GameScene {
    private final Map<String, SceneSegment> segments = new HashMap<>();

    public ScriptGameScene(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            TokenStream tokens = new TokenStream(stream);
            Parser parser = new Parser(tokens);
            while (!parser.hasReachedEnd()) {
                Pair<String, SceneSegment> segmentPair = parser.readSegment();
                segments.put(segmentPair.getKey(), segmentPair.getValue());
            }
        }
    }

    @Override
    public SceneSegment getInitialSegment() {
        throw new UnsupportedOperationException();
    }
}

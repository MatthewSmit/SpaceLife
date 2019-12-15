package com.spacelife.state.script.nodes;

import com.spacelife.state.script.Token;

public class SegmentNode extends Node {
    private final Token segmentId;

    public SegmentNode(Token segmentId) {
        this.segmentId = segmentId;
    }
}

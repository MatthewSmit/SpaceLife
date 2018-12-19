package com.spacelife.render;

import com.spacelife.state.Hoverable;

/**
 * Renders scene text in text format.
 */
public class TextRenderer extends Renderer {
    @Override
    protected void onTextChanged() {
    }

    @Override
    protected String renderNewline() {
        return "\n";
    }

    @Override
    protected String renderBreak() {
        return "\n\n";
    }

    @Override
    protected String renderHoverable(Hoverable hoverable, int index) {
        return hoverable.getText();
    }
}

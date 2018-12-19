package com.spacelife.render;

import com.spacelife.state.Hoverable;

/**
 * Renders scene text in HTML format.
 */
public class HtmlRenderer extends Renderer {

    @Override
    protected void onTextChanged() {
    }

    @Override
    protected String renderNewline() {
        return "<br>";
    }

    @Override
    protected String renderBreak() {
        return "<hr>";
    }

    @Override
    protected String renderHoverable(Hoverable hoverable, int index) {
        return "<span class='link' " +
                "onclick='app.click(event, " + index + ")' " +
                "onmousemove='app.mouseEnter(event, " + index + ")' " +
                "onmouseleave='app.mouseLeave(event, " + index + ")'>" +
                hoverable.getText() +
                "</span>";
    }

    @Override
    public String getText() {
        return "<html><head></head><body>"
                + super.getText()
//                + "<script type='text/javascript' src='http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js'></script>"
                + "</body></html>";
    }
}
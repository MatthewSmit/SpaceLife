package com.spacelife.screens;

import com.spacelife.render.Renderer;
import com.spacelife.render.WebViewRenderer;
import com.spacelife.state.Hoverable;
import javafx.geometry.Bounds;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.controlsfx.control.PopOver;

@JSInterop
public class SceneHtmlCallback {
    private final PopOver popOver;
    private final Renderer renderer;
    private final WebView mainText;

    public SceneHtmlCallback(PopOver popOver, WebViewRenderer renderer) {
        this.popOver = popOver;
        this.renderer = renderer;
        mainText = renderer.getWebView();
    }

    @JSInterop
    public void click(JSObject event, int i) {
        Hoverable hoverable = renderer.getHoverable(i);
        System.out.println("A");
    }

    @JSInterop
    public void mouseEnter(JSObject event, int i) {
        double x = (int) event.getMember("pageX");
        double y = (int) event.getMember("pageY");
        Hoverable hoverable = renderer.getHoverable(i);
        Bounds bounds = mainText.localToScreen(mainText.getBoundsInLocal());

        if (!popOver.isShowing()) {
            popOver.show(mainText);
        }

        popOver.setAnchorX(bounds.getMinX() + x + 15);
        popOver.setAnchorY(bounds.getMinY() + y - 30);

        popOver.setContentNode(new Text(hoverable.getExpandedText()));
    }

    @JSInterop
    public void mouseLeave(JSObject event, int i) {
        popOver.hide();
    }
}

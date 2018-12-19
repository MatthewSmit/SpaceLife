package com.spacelife.render;

import javafx.scene.web.WebView;

/**
 * Renders scene text in HTML format. Holds a WebView to auto update when the text changes.
 */
public class WebViewRenderer extends HtmlRenderer {
    private final WebView webView;

    public WebViewRenderer(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected void onTextChanged() {
        super.onTextChanged();

        webView.getEngine().loadContent(getText());
    }

    public WebView getWebView() {
        return webView;
    }
}

package com.spacelife.screens;

import com.spacelife.render.WebViewRenderer;
import com.spacelife.state.Game;
import com.spacelife.state.SceneState;
import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.controlsfx.control.PopOver;

import java.io.IOException;

public class SceneView {

    private PopOver popOver;

    @FXML
    public AnchorPane actionsHolder;

    @FXML
    public WebView mainText;

    @FXML
    public WebView actionDescription;

    private WebViewRenderer mainTextRenderer;

    private WebViewRenderer actionDescriptionRenderer;

    private ActionList actionList;

    public void initialize() throws IOException {
        createChildren();

        Game.getGame().addSceneSegmentChangeListener(this::redrawSegment);
        Game.getGame().addSceneChangeListener(this::redrawScene);

        createPopOver();

        WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
            System.out.println("Console: [" + sourceId + ':' + lineNumber + "] " + message);
        });

        mainTextRenderer = new WebViewRenderer(mainText);
        actionDescriptionRenderer = new WebViewRenderer(actionDescription);

        mainText.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            onWebStateChange(mainTextRenderer, newValue);
        });
        actionDescription.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            onWebStateChange(actionDescriptionRenderer, newValue);
        });

        SceneState currentScene = Game.getGame().getCurrentScene();
        redrawScene(currentScene);
    }

    private void createChildren() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/actionList.fxml"));
        Node node = loader.load();
        actionList = loader.getController();

        actionList.setActionSelected(action -> {
            if (action == null) {
                actionDescriptionRenderer.setText(null, null);
            } else {
                actionDescriptionRenderer.setText(Game.getGame().getCurrentScene(), action.getLongDescription());
            }
        });
        actionsHolder.getChildren().add(node);
    }

    private void createPopOver() {
        popOver = new PopOver();
        popOver.setAnimated(false);
    }

    public void onKeyPress(KeyEvent keyEvent) {
        if (!mainText.isFocused() && !actionDescription.isFocused()) {
            keyEvent.consume();
        }
    }

    public void onKeyRelease(KeyEvent keyEvent) {
        if (!mainText.isFocused() && !actionDescription.isFocused()) {
            keyEvent.consume();

            switch (keyEvent.getCode()) {
                case ENTER:
                case SPACE:
                    actionList.triggerSelection();
                    break;

                default:
                    // Do nothing
            }
        }
    }

    private void onWebStateChange(WebViewRenderer webViewRenderer, Worker.State newValue) {
        if (newValue == Worker.State.SUCCEEDED) {
            String styleSheet = SceneView.class.getResource("/css/style.css").toExternalForm();
            webViewRenderer.getWebView().getEngine().setUserStyleSheetLocation(styleSheet);

            JSObject window = (JSObject) webViewRenderer.getWebView().getEngine().executeScript("window");
            window.setMember("app", new SceneHtmlCallback(popOver, webViewRenderer));

            webViewRenderer.getWebView().getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
        }
    }

    private void redrawBase(SceneState currentScene) {
        actionList.redraw(currentScene);
    }

    private void redrawScene(SceneState currentScene) {

        mainTextRenderer.setText(currentScene, currentScene.getCurrentSegment().getText());

        redrawBase(currentScene);
    }

    private void redrawSegment(SceneState currentScene) {

        mainTextRenderer.addText(currentScene, currentScene.getCurrentSegment().getText());

        redrawBase(currentScene);
    }
}
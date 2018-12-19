package com.spacelife.render;

import com.spacelife.state.Hoverable;
import com.spacelife.state.SceneState;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders scene text.
 */
public abstract class Renderer {

    /**
     * A list of hoverable elements, used for popovers.
     */
    private final List<Hoverable> hoverables = new ArrayList<>();

    /**
     * The current rendered text.
     */
    private String text = "";

    /**
     * Clears the text and hoverable data.
     */
    public void clear() {
        hoverables.clear();
        text = "";
        onTextChanged();
    }

    /**
     * Renders and sets the given text with the given scene state.
     * @param sceneState The scene state for code execution.
     * @param text The raw scene text.
     */
    public void setText(SceneState sceneState, String text) {
        hoverables.clear();
        this.text = "";
        this.text = renderText(sceneState, text);
        onTextChanged();
    }

    /**
     * Renders and appends the given text with the given scene state.
     * @param sceneState The scene state for code execution.
     * @param text The raw scene text.
     */
    public void addText(SceneState sceneState, String text) {
        String newText = renderText(sceneState, text);
        if (this.text.isEmpty()) {
            this.text = newText;
        } else {
            this.text += renderBreak() + newText;
        }

        onTextChanged();
    }

    /**
     * Renders the given raw text.
     */
    private String renderText(SceneState sceneState, String text) {
        if (text == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            switch (c) {
                case '<':
                    i = readBracket(sb, text, i);
                    break;

                case '{':
                    i = readBrace(sceneState, sb, text, i);
                    break;

                case '\n':
                    sb.append(renderNewline());
                    break;

                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }

    private int readBrace(SceneState sceneState, StringBuilder sb, CharSequence text, int i) {
        if (text.charAt(i + 1) == '{') {
            sb.append('{');
            i++;
        } else {
            StringBuilder code = new StringBuilder();
            boolean foundEnd = false;
            for (i++; true; i++) {
                char c = text.charAt(i);
                switch (c) {
                    case '\'':
                        throw new UnsupportedOperationException();

                    case '"':
                        throw new UnsupportedOperationException();

                    case '}':
                        foundEnd = true;
                        break;

                    default:
                        code.append(c);
                        break;
                }

                if (foundEnd) {
                    break;
                }
            }

            Object scriptObject = executeCode(sceneState, code.toString());
            sb.append(renderScriptObject(scriptObject));
        }
        return i;
    }

    private String renderScriptObject(Object scriptObject) {
        if (scriptObject == null) {
            return "null";
        }

        if (scriptObject instanceof Hoverable) {
            int i = hoverables.size();
            hoverables.add((Hoverable) scriptObject);
            return renderHoverable((Hoverable)scriptObject, i);
        }

        return scriptObject.toString();
    }

    private static Object executeCode(SceneState sceneState, String code) {
        // TODO
        return sceneState.getNpc(code);
    }

    private static int readBracket(StringBuilder sb, String text, int i) {
        throw new UnsupportedOperationException();
    }

    protected abstract void onTextChanged();

    protected abstract String renderNewline();

    protected abstract String renderBreak();

    protected abstract String renderHoverable(Hoverable hoverable, int index);

    public String getText() {
        return text;
    }

    public Hoverable getHoverable(int i) {
        return hoverables.get(i);
    }
}

package com.spacelife.state.script;

import org.jetbrains.annotations.Nullable;

public class Token {
    private final TokenType type;
    private final @Nullable String text;

    public Token(TokenType type) {
        this.type = type;
        text = null;
    }

    public Token(TokenType type, @Nullable String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        if (text == null) {
            return type.toString();
        }

        return type + ": " + text;
    }

    public TokenType getType() {
        return type;
    }

    public @Nullable String getText() {
        return text;
    }
}

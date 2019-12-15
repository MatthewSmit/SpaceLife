package com.spacelife.state.script.nodes;

import com.spacelife.state.script.Token;

import java.util.ArrayList;
import java.util.List;

public class TextNode extends StatementNode {
    private final List<Token> tokens = new ArrayList<>();

    public void addToken(Token token) {
        tokens.add(token);
    }
}

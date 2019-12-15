package com.spacelife.state.script;

import com.spacelife.state.SceneSegment;
import com.spacelife.state.script.nodes.SegmentNode;
import com.spacelife.state.script.nodes.StatementNode;
import com.spacelife.state.script.nodes.TextNode;
import javafx.util.Pair;

public class Parser {
    private final TokenStream tokens;

    public Parser(TokenStream tokens) {
        this.tokens = tokens;
    }

    public boolean hasReachedEnd() {
        return tokens.peek().getType() == TokenType.EOF;
    }

    public Pair<String, SceneSegment> readSegment() {

        while (true) {
            Token token = tokens.read();

            System.out.println(token);
            System.out.flush();

            if (token.getType() == TokenType.EOF) {
                break;
            }
        }

//        SegmentNode segmentNode = readSegmentTitle();
//        while (true) {
//            Token token = tokens.peek();
//            if (token.getType() == TokenType.STATEMENT_START) {
//                throw new UnsupportedOperationException();
//            }
//
//            if (token.getType() == TokenType.EOF) {
//                break;
//            }
//
//            StatementNode statementNode = readStatement();
//            throw new UnsupportedOperationException();
//        }

        throw new UnsupportedOperationException();
    }

    private StatementNode readStatement() {
        TokenType tokenType = tokens.peek().getType();
        if (tokenType == TokenType.TEXT) {
            return readText();
        }

        throw new UnsupportedOperationException();
    }

    private TextNode readText() {
        TextNode textNode = new TextNode();
        Token token = tokens.expect(TokenType.TEXT);
        textNode.addToken(token);

        while (tokens.peek().getType() == TokenType.TEXT_ESCAPE) {
            throw new UnsupportedOperationException();
        }

        return textNode;
    }

    private SegmentNode readSegmentTitle() {
        tokens.expect(TokenType.STATEMENT_START);
        Token segmentId = tokens.expect(TokenType.IDENTIFIER);
        if (tokens.peek().getType() == TokenType.LEFT_PARANTHESIS) {
            throw new UnsupportedOperationException();
        }

        // TODO: must be on the same line
        if (tokens.peek().getType() == TokenType.COLON) {
            throw new UnsupportedOperationException();
        }

        return new SegmentNode(segmentId);
    }
}

package com.spacelife.state.script;

import com.spacelife.Utils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class TokenStream {
    private static final Map<String, TokenType> keywords = Utils.mapOf("var", TokenType.VAR);
    private static final Map<Character, TokenType> normalTokens = Utils.mapOf('(', TokenType.LEFT_PARANTHESIS,
            ')', TokenType.RIGHT_PARANTHESIS,
            '.', TokenType.DOT);

    private final String values;
    private int pointer;

    private int numberBraces;
    private final Stack<Integer> braceCount = new Stack<>();

    private final Queue<Token> tokens = new ArrayDeque<>();
    private StreamState currentState = StreamState.START_OF_LINE;

    public TokenStream(InputStream stream) throws IOException {

        byte[] data = IOUtils.toByteArray(stream);
        values = new String(data, StandardCharsets.UTF_8);
    }

    public Token peek() {
        while (tokens.isEmpty()) {
            readNext();
        }
        return tokens.peek();
    }

    public Token read() {
        while (tokens.isEmpty()) {
            readNext();
        }
        return tokens.poll();
    }

    private void readNext() {

        int chr = peekChar();
        if (chr < 0) {
            Token nextToken = new Token(TokenType.EOF);
            tokens.add(nextToken);
            return;
        }

        skipWhitespace();

        chr = peekChar();

        if (chr == ':') {
            readChar();
            if (currentState == StreamState.START_OF_LINE) {
                throw new UnsupportedOperationException();
            } else {
                throw new UnsupportedOperationException();
            }
//            return;
        }

        if (chr == '<') {
            readChar();
            if (currentState == StreamState.START_OF_LINE) {
                Token nextToken = new Token(TokenType.STATEMENT_START);
                tokens.add(nextToken);
                readSegmentName();
            } else {
                Token nextToken = new Token(TokenType.COLON);
                tokens.add(nextToken);
            }
            return;
        }

        if (chr == '#' && currentState == StreamState.START_OF_LINE) {
            readChar();
            currentState = StreamState.IN_CODE;
            readText(false);
            return;
        }

        if (currentState == StreamState.START_OF_LINE) {
            currentState = StreamState.IN_CODE;
        }

        if (Character.isLetterOrDigit(chr) || chr == '_') {
            Token token = readIdentifierImpl();
            TokenType keyword = keywords.get(token.getText());
            if (keyword == null) {
                tokens.add(token);
            } else {
                tokens.add(new Token(keyword));
            }
        } else if (chr == '=') {
            readChar();
            if (peekChar() == '=') {
                readChar();
                Token token = new Token(TokenType.EQUALITY);
                tokens.add(token);
            } else {
                Token token = new Token(TokenType.ASSIGNMENT);
                tokens.add(token);
            }
        } else if (chr == ';') {
            readChar();
            Token token = new Token(TokenType.LEFT_BRACE);
            tokens.add(token);
            currentState = StreamState.START_OF_LINE;
        } else if (chr == '{') {
            readChar();
            Token token = new Token(TokenType.LEFT_BRACE);
            tokens.add(token);
            numberBraces++;
        } else if (chr == '}') {
            readChar();

            if (numberBraces == 0) {
                numberBraces = braceCount.pop();
                readText(true);
            } else {
                Token token = new Token(TokenType.RIGHT_BRACE);
                tokens.add(token);
                numberBraces--;
            }
        } else {
            TokenType type = normalTokens.get((char)chr);
            if (type != null) {
                readChar();
                Token token = new Token(type);
                tokens.add(token);
            } else {
                System.out.println("XXX: " + (char)chr);
                throw new UnsupportedOperationException();
            }
        }
    }

    private void readText(boolean isContinuation) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int chr = peekChar();
            if (chr < 0) {
                throw new UnsupportedOperationException();
            }

            switch (chr) {
                case '\r':
                    break;

                case '\n':
                    Token token = new Token(isContinuation ? TokenType.TEXT_CONTINUE : TokenType.TEXT, sb.toString());
                    tokens.add(token);
                    currentState = StreamState.START_OF_LINE;
                    return;

                case '\\':
                    throw new UnsupportedOperationException();

                case '{':
                    token = new Token(isContinuation ? TokenType.TEXT_CONTINUE : TokenType.TEXT, sb.toString());
                    tokens.add(token);
                    token = new Token(TokenType.TEXT_ESCAPE);
                    tokens.add(token);
                    readChar();
                    currentState = StreamState.IN_TEXT_ESCAPE;
                    braceCount.push(numberBraces);
                    numberBraces = 0;
                    return;

                default:
                    sb.append((char)chr);
                    readChar();
                    break;
            }
        }
    }

    private void skipWhitespace() {
        while (true) {
            int chr = peekChar();
            if (chr == '\n') {
                readChar();
            } else if (Character.isWhitespace(chr)) {
                readChar();
            } else {
                break;
            }
        }
    }

    private void readSegmentName() {
        readIdentifier();

        if (peekChar() == '\n') {
            return;
        }

        if (peekChar() != ':') {
            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }

    private void readIdentifier() {
        tokens.add(readIdentifierImpl());
    }

    private Token readIdentifierImpl() {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int chr = peekChar();
            if (chr < 0) {
                throw new UnsupportedOperationException();
            }

            if (Character.isLetterOrDigit(chr) || chr == '_') {
                sb.append((char)chr);
                readChar();
            } else {
                return new Token(TokenType.IDENTIFIER, sb.toString());
            }
        }
    }

    private int readChar() {
        if (pointer < 0 || pointer >= values.length()) {
            return -1;
        }

        // TODO: Unicode > 0xFFFF
        return values.charAt(pointer++);
    }

    private int peekChar() {
        if (pointer < 0 || pointer >= values.length()) {
            return -1;
        }

        // TODO: Unicode > 0xFFFF
        return values.charAt(pointer);
    }

    public Token expect(TokenType type) {
        Token token = read();
        if (token.getType() != type) {
            throw new UnsupportedOperationException();
        }
        return token;
    }
}

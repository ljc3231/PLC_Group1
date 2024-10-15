package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptionFiles.JottException;

import java.util.ArrayList;

public class NumberNode implements Operand {
    private final String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public static NumberNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("NumberNode");
        }

        Token currentToken = tokens.get(0);
        String value = currentToken.getToken();

        // Check if token is NUMBER
        if (!currentToken.getTokenType().equals(TokenType.NUMBER)) {
            throw new JottException("NumberNode", "Error: Expected NUMBER token but found " + currentToken.getTokenType(), currentToken.getLineNum());
        }

        tokens.remove(0);
        return new NumberNode(value);
    }

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

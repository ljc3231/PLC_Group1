package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode implements JottTree {
    private final String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public static NumberNode parse(ArrayList<Token> tokens) {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            System.err.println("Error: Expected a number but found nothing.");
            return null;
        }

        Token currentToken = tokens.get(0);
        String value = currentToken.getToken();

        // Check if token is NUMBER
        if (!currentToken.getTokenType().equals(TokenType.NUMBER)) {
            System.err.println("Error: Expected NUMBER token");
            return null;
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

package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IdNode implements JottTree {
    private final String id;

    public IdNode(String id) {
        this.id = id;
    }

    public static IdNode parse(ArrayList<Token> tokens) {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            System.err.println("Error: No tokens");
            return null;
        }

        Token currentToken = tokens.get(0);
        String id = currentToken.getToken();

        // Check if token is a ID_KEYWORD
        if (!currentToken.getTokenType().equals(TokenType.ID_KEYWORD)) {
            System.err.println("Error: Expected ID_KEYWORD");
            return null;
        }

        tokens.remove(0);
        return new IdNode(id);
    }

    @Override
    public String convertToJott() {
        return id;
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

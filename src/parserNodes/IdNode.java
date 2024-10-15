package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptionFiles.JottException;

import java.util.ArrayList;

public class IdNode implements OperandNode {
    private final String id;

    public IdNode(String id) {
        this.id = id;
    }

    public static IdNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("IdNode");
        }

        Token currentToken = tokens.get(0);
        String id = currentToken.getToken();

        // Check if token is a ID_KEYWORD
        if (!currentToken.getTokenType().equals(TokenType.ID_KEYWORD)) {
            throw new JottException("IdNode", "Expected: ID_KEYWORD, but got " + currentToken.getTokenType());
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

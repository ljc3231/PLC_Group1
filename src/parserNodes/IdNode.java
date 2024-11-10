package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class IdNode implements OperandNode {
    private final String id;
    private String exprType;
    public final static String FILENAME = "IdNode";

    public IdNode(String id) {
        this.id = id;
    }

    @Override
    public String getExprType() {
        return exprType;
    }

    @Override
    public void setExprType(String type) {
        this.exprType = type;
    }

    public static IdNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        Token currentToken = tokens.get(0);
        String id = currentToken.getToken();

        // Check if token is a ID_KEYWORD
        if (!currentToken.getTokenType().equals(TokenType.ID_KEYWORD)) {
            throw new JottException(true, FILENAME, "Expected: ID_KEYWORD, but got " + currentToken.getTokenType(), currentToken.getLineNum());
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
        return !id.isEmpty() && Character.isLowerCase(id.charAt(0));
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

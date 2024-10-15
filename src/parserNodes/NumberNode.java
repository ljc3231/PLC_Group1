package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class NumberNode implements OperandNode {
    private final String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public static NumberNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("NumberNode");
        }

        boolean isNeg = false;
        Token currentToken = tokens.get(0);
        if (currentToken.getToken().equals("-")) {
            tokens.remove(0);
            isNeg = true;
        }
        
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("NumberNode");
        }
        currentToken = tokens.get(0);

        // Check if token is NUMBER
        if (!currentToken.getTokenType().equals(TokenType.NUMBER)) {
            throw new JottException("NumberNode", "Error: Expected NUMBER token but found " + currentToken.getTokenType(), currentToken.getLineNum());
        }

        String value = currentToken.getToken();

        tokens.remove(0);
        if (isNeg) {
            return new NumberNode("-" + value);
        }
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

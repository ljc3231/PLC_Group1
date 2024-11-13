package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class NumberNode implements OperandNode {
    private final String value;
    private String exprType;
    public final static String FILENAME = "NumberNode";

    public NumberNode(String value) {
        this.value = value;
    }

    @Override
    public String getExprType() {
        return this.exprType;
    }

    public static NumberNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        boolean isNeg = false;
        Token currentToken = tokens.get(0);
        if (currentToken.getToken().equals("-")) {
            tokens.remove(0);
            isNeg = true;
        }
        
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }
        currentToken = tokens.get(0);

        // Check if token is NUMBER
        if (!currentToken.getTokenType().equals(TokenType.NUMBER)) {
            throw new JottException(false, FILENAME, "Error: Expected NUMBER token but found " + currentToken.getTokenType(), currentToken.getLineNum());
        }

        String value = currentToken.getToken();

        tokens.remove(0);
        if (isNeg) {
            value = "-" + value;
        }
        NumberNode numberNode = new NumberNode(value);
        if (value.contains(".")) {
            numberNode.exprType = "Double";
        }else{
            numberNode.exprType = "Integer";
        }
        return numberNode;
    }

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public boolean validateTree() {
        return exprType != null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

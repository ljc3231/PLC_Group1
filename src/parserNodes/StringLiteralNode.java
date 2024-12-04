package parserNodes;

import exceptionFiles.*;
import java.util.*;
import provided.*;

public class StringLiteralNode implements ExpressionNode {
    private final String str;
    private final static String FILENAME = "StringLiteralNode";

    public StringLiteralNode(String str) {
        this.str = str;
    }

    @Override
    public String getExprType() {
        return "String";
    }

    public static StringLiteralNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()){
            throw new EndOfFileException("String");
        }
        Token str = tokens.get(0);
        if (!str.getTokenType().equals(TokenType.STRING)){
            throw new JottException(true, FILENAME,"Expected string", tokens.get(0).getLineNum());
        }

        String string = str.getToken().replace("\"", "");
        tokens.remove(0);
        return new StringLiteralNode(string);
    }

    @Override
    public String convertToJott() {
        return this.str;
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public String execute() {
        return this.str;
    }
}

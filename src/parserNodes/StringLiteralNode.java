package parserNodes;

import provided.*;
import java.util.*;

public class StringLiteralNode implements JottTree{
    private final Token str;

    public StringLiteralNode(Token str) {this.str = str;}

    public static StringLiteralNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()){
            System.out.println("implement error");
            return null;
        }
        Token str = tokens.get(0);
        if (!str.getTokenType().equals(TokenType.STRING)){
            System.err.println("implement error");
            return null;
        }

        tokens.remove(0);
        return new StringLiteralNode(str);
    }

    @Override
    public String convertToJott() {
        return this.str.getToken();
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

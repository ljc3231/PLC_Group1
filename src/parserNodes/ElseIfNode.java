package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseIfNode implements JottTree {
    private final ExpressionNode condition;
    private final BodyNode body;

    public ElseIfNode (ExpressionNode expr, BodyNode body) {
        this.condition = expr;
        this.body = body;
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) {
        // parsing IF
        // empty arraylist and elseif string check done in IFNode
        tokens.remove(0); //removing elseif token

        if (!tokens.get(1).getTokenType().equals(TokenType.L_BRACKET)) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0); //removing bracket token

        ExpressionNode condition = ExpressionNode.parse(tokens);

        if (!tokens.get(1).getTokenType().equals(TokenType.R_BRACKET)) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0); //removing bracket token

        if (!tokens.get(1).getTokenType().equals(TokenType.L_BRACE)) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0); //removing brace token

        BodyNode body = BodyNode.parse(tokens);

        if (!tokens.get(1).getTokenType().equals(TokenType.R_BRACE)) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0); //removing brace token
        return new ElseIfNode(condition, body);;
    }

    @Override
    public String convertToJott() {

        String s = "Elseif";
        s += "[" + this.condition.convertToJott() + "]";
        s += "{" + this.body.convertToJott() + "}";

        return s;
    }

    @Override
    public boolean validateTree() {
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

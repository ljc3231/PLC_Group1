package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {
    private final ExpressionNode expression;  // Represents an expression in the parameter tail

    public ParamsTNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public static ParamsTNode parse(ArrayList<Token> tokens) {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            System.err.println("Error: no tokens");
        }

        // COMMA token
        if (!tokens.get(0).getToken().equals(TokenType.COMMA)) {
            System.err.println("Error: Expected COMMA");
        }
        tokens.remove(0);

        // ExpressionNode
        ExpressionNode expression = ExpressionNode.parse(tokens);
        if (expression == null) {
            System.err.println("Error: Expected ExpressionNode");
            return null;
        }

        return new ParamsTNode(expression);


    }
    @Override
    public String convertToJott() {
        return ", " + expression.convertToJott();
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

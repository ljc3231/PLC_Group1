package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptionFiles.JottException;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {
    private final ExpressionNode expression;

    public ParamsTNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public static ParamsTNode parse(ArrayList<Token> tokens) throws JottException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new JottException("ParamsTNode", "Error: no tokens available to parse.");
        }

        // COMMA token check
        if (!tokens.get(0).getToken().equals(",")) {
            throw new JottException("ParamsTNode", "Error: Expected COMMA token but found " + tokens.get(0).getToken());
        }
        tokens.remove(0);

        // Parse ExpressionNode
        ExpressionNode expression = ExpressionNode.parse(tokens);
        if (expression == null) {
            throw new JottException("ParamsTNode", "Error: Expected ExpressionNode");
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

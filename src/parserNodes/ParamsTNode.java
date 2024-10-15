package parserNodes;

import provided.*;
import exceptionFiles.*;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {
    private final ExpressionNode expression;

    public ParamsTNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public static ParamsTNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("ParamsTNode");
        }

        // COMMA token check
        tryTerminal(tokens, ",", "ParamsTNode");

        // Parse ExpressionNode
        ExpressionNode expression = ExpressionNode.parse(tokens);

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

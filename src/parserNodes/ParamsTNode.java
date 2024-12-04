package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;

public class ParamsTNode implements JottTree, ParseTerminal {
    private final ExpressionNode expression;
    public final static String FILENAME = "ParamsTNode";

    public ParamsTNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public static ParamsTNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        // COMMA token check
        ParseTerminal.parseTerminal(tokens, ",", FILENAME);

        // Parse ExpressionNode
        ExpressionNode expression = ExpressionNode.parse(tokens);

        return new ParamsTNode(expression);
    }

    public String getType() throws JottException {
        return expression.getExprType();
    }

    @Override
    public String convertToJott() {
        return ", " + expression.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // Check if expression is non-null and valid
        return expression != null && expression.validateTree();
    }

    @Override
    public String execute() throws JottException {
        return expression.execute();
    }
}

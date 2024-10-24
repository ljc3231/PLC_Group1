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

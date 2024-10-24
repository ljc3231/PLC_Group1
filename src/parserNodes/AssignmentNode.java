package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;

public class AssignmentNode implements BodyStatementNode, ParseTerminal {
    private final IdNode id;
    private final JottTree expression;
    public final static String FILENAME = "AssignmentNode";

    public AssignmentNode(IdNode id, JottTree expr) {
        this.id = id;
        this.expression = expr;
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        // Parse IdNode
        IdNode id = IdNode.parse(tokens);

        // Check for ASSIGN token
        ParseTerminal.parseTerminal(tokens, "=", FILENAME);

        // Parse ExpressionNode
        JottTree expression = ExpressionNode.parse(tokens);

        // Check for Semicolon
        ParseTerminal.parseTerminal(tokens, ";", FILENAME);

        return new AssignmentNode(id, expression);
    }

    @Override
    public String convertToJott() {
        return id.convertToJott() + " = " + expression.convertToJott() + ";";
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

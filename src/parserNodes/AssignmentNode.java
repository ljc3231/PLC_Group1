package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptionFiles.JottException;

import java.util.ArrayList;

public class AssignmentNode implements BodyStatementNode {
    private final IdNode id;
    private final JottTree expression;

    public AssignmentNode(IdNode id, JottTree expr) {
        this.id = id;
        this.expression = expr;
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("AssignmentNode");
        }

        // Parse IdNode
        IdNode id = IdNode.parse(tokens);
        if (id == null) {
            throw new JottException("AssignmentNode", "Error: Expected IdNode");
        }

        // Check for ASSIGN token
        tryTerminal(tokens, "=", "AssignmentNode");

        // Parse ExpressionNode
        JottTree expression = ExpressionNode.parse(tokens);
        if (expression == null) {
            throw new JottException("AssignmentNode", "Error: Expected ExpressionNode");
        }

        // Check for Semicolon
        tryTerminal(tokens, ";", AssignmentNode);

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

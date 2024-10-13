package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class AssignmentNode implements BodyStatementNode {
    private final IdNode id;
    private final JottTree expression;

    public AssignmentNode(IdNode id, JottTree expr) {
        this.id = id;
        this.expression = expr;
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            System.err.println("Error: no tokens");
            return null;
        }

        // IdNode
        IdNode id = IdNode.parse(tokens);
        if (id == null) {
            System.err.println("Error: Expected IdNode");
            return null;
        }

        // ASSIGN token
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals(TokenType.ASSIGN)) {
            System.err.println("Error: Expected ASSIGN");
            return null;
        }
        tokens.remove(0);

        // Expression
        JottTree expression = ExpressionNode.parse(tokens);
        if (expression == null) {
            System.err.println("Error: Expected ExpressionNode");
            return null;
        }

        // Semicolon
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals(TokenType.SEMICOLON)) {
            System.err.println("Error: Expected SEMICOLON");
            return null;
        }
        tokens.remove(0);

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

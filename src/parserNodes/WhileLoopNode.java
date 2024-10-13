package parserNodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class WhileLoopNode implements BodyStatementNode {
    ExpressionNode condition;
    BodyNode body;
    public WhileLoopNode(ExpressionNode condition, BodyNode body) {
        this.condition = condition;
        this.body = body;
    }

    public static WhileLoopNode parse(ArrayList<Token> tokens){
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("While")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        //parsing [<expression>]
        if (!tokens.get(1).getToken().equals("[")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);
        ExpressionNode condition = ExpressionNode.parse(tokens);
        if (!tokens.get(1).getToken().equals("]")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);
        //parsing [<body>]
        if (!tokens.get(1).getToken().equals("{")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);
        BodyNode body = BodyNode.parse(tokens);
        if (!tokens.get(1).getToken().equals("}")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        return new WhileLoopNode(condition, body);
    }

    @Override
    public String convertToJott() {
        String s = "While";
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

package parserNodes;

import exceptionFiles.*;
import provided.*;
import java.util.ArrayList;

public class ElseIfNode implements JottTree {
    public final static String FILENAME = "ElseIfNode";
    private final ExpressionNode condition;
    private final BodyNode body;

    public ElseIfNode (ExpressionNode expr, BodyNode body) {
        this.condition = expr;
        this.body = body;
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        JottTree.tryTerminal(tokens, "ElseIf", FILENAME);

        // parsing [<expressionNode>]
        JottTree.tryTerminal(tokens, "[", FILENAME);
        ExpressionNode condition = ExpressionNode.parse(tokens);
        JottTree.tryTerminal(tokens, "]", FILENAME);

        //parsing {<bodyNode>}
        JottTree.tryTerminal(tokens, "{", FILENAME);
        BodyNode body = BodyNode.parse(tokens);
        JottTree.tryTerminal(tokens, "}", FILENAME);

        return new ElseIfNode(condition, body);
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

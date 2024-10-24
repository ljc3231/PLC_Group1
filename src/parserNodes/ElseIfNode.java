package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;

public class ElseIfNode implements JottTree, ParseTerminal {
    public final static String FILENAME = "ElseIfNode";
    private final ExpressionNode condition;
    private final BodyNode body;

    public ElseIfNode (ExpressionNode expr, BodyNode body) {
        this.condition = expr;
        this.body = body;
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        ParseTerminal.parseTerminal(tokens, "ElseIf", FILENAME);

        // parsing [<expressionNode>]
        ParseTerminal.parseTerminal(tokens, "[", FILENAME);
        ExpressionNode condition = ExpressionNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "]", FILENAME);

        //parsing {<bodyNode>}
        ParseTerminal.parseTerminal(tokens, "{", FILENAME);
        BodyNode body = BodyNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "}", FILENAME);

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

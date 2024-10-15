package parserNodes;

import exceptionFiles.*;
import provided.*;

import java.util.ArrayList;

public class WhileLoopNode implements BodyStatementNode {
    public final static String FILENAME = "WhileLoopNode";
    ExpressionNode condition;
    BodyNode body;
    public WhileLoopNode(ExpressionNode condition, BodyNode body) {
        this.condition = condition;
        this.body = body;
    }

    public static WhileLoopNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("While Loop");
        }

        JottTree.tryTerminal(tokens, "While", FILENAME );

        //parsing [<expression>]
        JottTree.tryTerminal(tokens, "[", FILENAME );
        ExpressionNode condition = ExpressionNode.parse(tokens);
        JottTree.tryTerminal(tokens, "]", FILENAME );

        //parsing {<body>}
        JottTree.tryTerminal(tokens, "{", FILENAME );
        BodyNode body = BodyNode.parse(tokens);
        JottTree.tryTerminal(tokens, "}", FILENAME );

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

package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;

public class WhileLoopNode implements BodyStatementNode, ParseTerminal {
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

        ParseTerminal.parseTerminal(tokens, "While", FILENAME );

        //parsing [<expression>]
        ParseTerminal.parseTerminal(tokens, "[", FILENAME );
        ExpressionNode condition = ExpressionNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "]", FILENAME );

        //parsing {<body>}
        ParseTerminal.parseTerminal(tokens, "{", FILENAME );
        BodyNode body = BodyNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "}", FILENAME );

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
        return condition.validateTree() && body.validateTree();
    }

    @Override
    public String execute() {
        String result = "";
        while (condition.execute().equals("True")){
            if (body.execute() != null){
                result = body.execute();
            }
        }
        return result;
    }

    @Override
    public boolean validReturn() {
        return false;
    }
}

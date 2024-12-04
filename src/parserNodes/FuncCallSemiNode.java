package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import helpers.*;
import java.util.ArrayList;
import provided.Token;

public class FuncCallSemiNode implements BodyStatementNode, ParseTerminal {
    private FuncCallNode funcCallNode;
    public static FuncCallSemiNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        FuncCallNode f = FuncCallNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, ";", "FuncCallSemiNode");
        return new FuncCallSemiNode(f);
    }
    public FuncCallSemiNode(FuncCallNode f) {
        funcCallNode = f;
    }
    @Override
    public String convertToJott() {
        return funcCallNode.convertToJott() + ";";
    }

    @Override
    public boolean validateTree() {
        return funcCallNode.validateTree();
    }

    @Override
    public String execute() throws JottException {
        return funcCallNode.execute();
    }

    @Override
    public boolean validReturn() {
        return this.funcCallNode.validReturn();
    }
}

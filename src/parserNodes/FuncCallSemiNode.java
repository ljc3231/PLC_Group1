package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FuncCallSemiNode implements BodyStatementNode {
    private FuncCallNode funcCallNode;
    public static FuncCallSemiNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        FuncCallNode f = FuncCallNode.parse(tokens);
        JottTree.tryTerminal(tokens, ";", "FuncCallSemiNode");
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
        return false;
    }

    @Override
    public void execute() {

    }
}

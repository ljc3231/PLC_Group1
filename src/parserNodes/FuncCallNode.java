package parserNodes;

import exceptionFiles.*;
import java.util.*;
import provided.*;

public class FuncCallNode implements BodyStatementNode, OperandNode {
    private final IdNode functionName;
    private final ParamsNode params;
    public final static String FILENAME = "FuncCallNode";

    public FuncCallNode(IdNode functionName, ParamsNode params) {
        this.functionName = functionName;
        this.params = params;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }
        // FC_HEADER token
        JottTree.tryTerminal(tokens, "::", FILENAME);

        // Parse IdNode
        IdNode functionName = IdNode.parse(tokens);

        // Left Bracket check
        JottTree.tryTerminal(tokens, "[", FILENAME);

        // Parse ParamsNode
        ParamsNode params = ParamsNode.parse(tokens);
        
        // Right Bracket check
        JottTree.tryTerminal(tokens, "]", FILENAME);

        return new FuncCallNode(functionName, params);
    }

    @Override
    public String convertToJott() {
        return "::" + functionName.convertToJott() + "[" + params.convertToJott() + "];";
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

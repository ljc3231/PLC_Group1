package parserNodes;

import provided.*;
import exceptionFiles.*;
import java.util.*;

public class FuncCallNode implements BodyStatementNode {
    private final IdNode functionName;
    private final ParamsNode params;

    public FuncCallNode(IdNode functionName, ParamsNode params) {
        this.functionName = functionName;
        this.params = params;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("FuncCallNode");
        }
        // FC_HEADER token
        tryTerminal(tokens, "::", "FuncCallNode");

        // Parse IdNode
        IdNode functionName = IdNode.parse(tokens);

        // Left Bracket check
        tryTerminal(tokens, "[", "FuncCallNode");

        // Parse ParamsNode
        ParamsNode params = ParamsNode.parse(tokens);
        
        // Right Bracket check
        tryTerminal(tokens, "]", "FuncCallNode");

        return new FuncCallNode(functionName, params);
    }

    @Override
    public String convertToJott() {
        return "::" + functionName.convertToJott() + "[" + params.convertToJott() + "]";
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

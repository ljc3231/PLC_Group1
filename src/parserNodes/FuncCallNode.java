package parserNodes;

import provided.*;
import exceptionFiles.JottException;
import java.util.*;

public class FuncCallNode implements BodyStatementNode {
    private final IdNode functionName;
    private final ParamsNode params;

    public FuncCallNode(IdNode functionName, ParamsNode params) {
        this.functionName = functionName;
        this.params = params;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws JottException {
        // FC_HEADER token
        if (tokens.isEmpty() || !tokens.get(0).getTokenType().equals(TokenType.FC_HEADER)) {
            throw new JottException("FuncCallNode", "Error: Expected FC_HEADER token");
        }
        tokens.remove(0);

        // Parse IdNode
        IdNode functionName = IdNode.parse(tokens);
        if (functionName == null) {
            throw new JottException("FuncCallNode", "Error: Expected IdNode");
        }

        // Left Bracket check
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals("[")) {
            throw new JottException("FuncCallNode", "Error: Expected '[");
        }
        tokens.remove(0);

        // Parse ParamsNode
        ParamsNode params = ParamsNode.parse(tokens);
        
        // Right Bracket check
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals("]")) {
            throw new JottException("FuncCallNode", "Error: Expected ']'");
        }
        tokens.remove(0);

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

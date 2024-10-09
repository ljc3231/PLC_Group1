package parserNodes;

import provided.*;
import java.util.*;

public class FuncCallNode implements JottTree {
    private final IdNode functionName;
    private final ParamsNode params;


    public FuncCallNode(IdNode functionName, ParamsNode params) {
        this.functionName = functionName;
        this.params = params;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) {
        // FC_HEADER token
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals(TokenType.FC_HEADER)) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        // IdNode
        IdNode functionName = IdNode.parse(tokens);
        if (functionName == null) {
            System.err.println("Error: Expcted IdNode");
            return null;
        }
        
        // Left Bracket
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals("[")) {
            System.err.println("Error: Expected '['");
            return null;
        }
        tokens.remove(0);
        
        // ParamsNode
        ParamsNode params = ParamsNode.parse(tokens);

        // Right Bracket
        if (tokens.isEmpty() || !tokens.get(0).getToken().equals("]")) {
            System.err.println("Error: Expected ']'");
            return null;
        }
        tokens.remove(0);

        return null;
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

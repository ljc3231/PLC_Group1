package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsNode implements JottTree {
    private final ArrayList<JottTree> params;

    public ParamsNode(ArrayList<JottTree> params) {
        this.params = params;
    }

    public static ParamsNode parse(ArrayList<Token> tokens) {
        ArrayList<JottTree> parsedParams = new ArrayList<>();
        
        // Check if the parameter list is empty
        if (tokens.isEmpty()) {
            return new ParamsNode(parsedParams);
        }

        // Check if there are no params
        if (tokens.get(0).getToken().equals("]")) {
            return new ParamsNode(parsedParams);
        }

        // Loop through params
        while (true) {
            // ExpressionNode
            JottTree expr = ExpressionNode.parse(tokens);
            if (expr == null) {
                System.err.println("Error: Expected ExpressionNode");
                return null;
            }
            parsedParams.add(expr);

            // COMMA
            if (tokens.isEmpty() || !tokens.get(0).getToken().equals(TokenType.COMMA)) {
                break;
            }
            tokens.remove(0);
        }

        return new ParamsNode(parsedParams);
    }
    @Override
    public String convertToJott() {
        String result = "";
        for (int i = 0; i < params.size(); i++) {
            result += params.get(i).convertToJott();
            if (i < params.size() - 1) {
                result += ", ";
            }
        }
        return result.toString();
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

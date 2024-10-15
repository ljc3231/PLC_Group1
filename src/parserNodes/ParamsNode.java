package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptionFiles.JottException;

import java.util.ArrayList;

public class ParamsNode implements JottTree {
    private final ArrayList<JottTree> params;

    public ParamsNode(ArrayList<JottTree> params) {
        this.params = params;
    }

    public static ParamsNode parse(ArrayList<Token> tokens) throws JottException {
        ArrayList<JottTree> parsedParams = new ArrayList<>();
        
        // Check if the parameter list is empty
        if (tokens.isEmpty()) {
            return new ParamsNode(parsedParams);
        }

        // Check if there are no params
        if (tokens.get(0).getToken().equals("]")) {
            return new ParamsNode(parsedParams);
        }

        // Parse ExpressionNode
        JottTree expr = ExpressionNode.parse(tokens);
        if (expr == null) {
            throw new JottException("ParamsNode", "Error: Expected ExpressionNode");
        }
        parsedParams.add(expr);

        // Loop through params
        while (true) {
            if (tokens.get(0).getToken().equals("]")) {
                break;
            }
            // Parse Params_T
            JottTree params_t = ParamsTNode.parse(tokens);
            parsedParams.add(params_t);
        }

        return new ParamsNode(parsedParams);
    }

    @Override
    public String convertToJott() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            result.append(params.get(i).convertToJott());
            if (i < params.size() - 1) {
                result.append(", ");
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

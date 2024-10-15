package parserNodes;

import provided.*;
import exceptionFiles.*;

import java.util.ArrayList;

public class ParamsNode implements JottTree {
    private final ArrayList<JottTree> params;

    public ParamsNode(ArrayList<JottTree> params) {
        this.params = params;
    }

    public static ParamsNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        ArrayList<JottTree> parsedParams = new ArrayList<>();
        
        // Check if the parameter list is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException("ParamsNode");
        }

        // Check if there are no params
        if (tokens.get(0).getToken().equals("]")) {
            return new ParamsNode(parsedParams);
        }

        // Parse ExpressionNode
        JottTree expr = ExpressionNode.parse(tokens);
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
        String result = "";
        for (int i = 0; i < params.size(); i++) {
            result += (params.get(i).convertToJott());
        }
        return result;
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

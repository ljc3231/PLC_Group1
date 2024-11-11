package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class ParamsNode implements JottTree {
    private final ExpressionNode expression;
    private final ArrayList<ParamsTNode> params;
    private final boolean exprExists;
    private final boolean paramsTExists;
    public final static String FILENAME = "ParamsNode";

    public ParamsNode() {
        this.expression = null;
        this.params = null;
        this.exprExists = false;
        this.paramsTExists = false;
    }

    public ParamsNode(ExpressionNode expr) {
        this.expression = expr;
        this.params = null;
        this.exprExists = true;
        this.paramsTExists = false;
    }

    public ParamsNode(ExpressionNode expr, ArrayList<ParamsTNode> params) {
        this.expression = expr;
        this.params = params;
        this.exprExists = true;
        this.paramsTExists = true;
    }

    public static ParamsNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        ArrayList<ParamsTNode> parsedParams = new ArrayList<>();
        
        // Check if the parameter list is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        // Check if there are no params
        if (tokens.get(0).getToken().equals("]")) {
            return new ParamsNode();
        }

        // Parse ExpressionNode
        ExpressionNode expr = ExpressionNode.parse(tokens);

        if (tokens.get(0).getToken().equals("]")) {
            return new ParamsNode(expr);
        }

        // Loop through params
        while (true) {
            if (tokens.get(0).getToken().equals("]")) {
                break;
            }
            // Parse Params_T
            ParamsTNode params_t = ParamsTNode.parse(tokens);
            parsedParams.add(params_t);
        }

        return new ParamsNode(expr, parsedParams);
    }

    public ArrayList<String> getTypes() throws JottException {
        if (!exprExists) {
            return null;
        }
        ArrayList<String> types = new ArrayList<>();
        types.add(expression.getExprType());
        if (!paramsTExists) {
            return types;
        }
        for (ParamsTNode p : params) {
            types.add(p.getType());
        }
        return types;
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
        if (!expression.validateTree()) {
            return false;
        }
        for (JottTree param : params) {
            if (!param.validateTree()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

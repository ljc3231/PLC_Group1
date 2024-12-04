package parserNodes;

import JottMain.Jott;
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

    public boolean exists() {
        return this.exprExists;
    }

    @Override
    public String convertToJott() {
        String result = "";
        if (!exprExists) {
            return result;
        }

        result += expression.convertToJott();

        if (!paramsTExists) {
            return result;
        }

        for (int i = 0; i < params.size(); i++) {
            result += (params.get(i).convertToJott());
        }

        return result;
    }

    @Override
    public boolean validateTree() {
        if (!exprExists){
            return true;
        }
        if (exprExists && !expression.validateTree()) {
            return false;
        }

        if (!paramsTExists) {
            return true;
        }

        for (JottTree param : params) {
            if (!param.validateTree()) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public String execute() throws JottException {
        // If there are no parameters, return an empty string
        if (!exprExists) {
            return "";
        }

        // Execute the expression node and gather its result
        StringBuilder result = new StringBuilder();
        result.append(expression.execute());

        // If no additional parameters exist, return the result of the single expression
        if (!paramsTExists) {
            return result.toString();
        }

        // Execute each ParamsTNode and append the results
        for (ParamsTNode param : params) {
            result.append(", ").append(param.execute());
        }

        return result.toString();
    }
}

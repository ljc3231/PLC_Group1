package parserNodes;
import provided.*;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {
    private final IdNode funcName;
    private final FuncDefParamsNode parameters;
    private final FunctionReturnNode returnType;
    private final FBodyNode funcBody;

    public FunctionDefNode(IdNode fN, FuncDefParamsNode p, FunctionReturnNode rT, FBodyNode fB) {
        this.funcName = fN;
        this.parameters = p;
        this.returnType = rT;
        this.funcBody = fB;
    }

    public static FunctionDefNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("Def")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        IdNode funcName = IdNode.parse(tokens);

        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("[")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        FuncDefParamsNode parameters = FuncDefParamsNode.parse(tokens);

        if (tokens.size() < 2) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("]")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        if (!tokens.get(1).getToken().equals(":")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        FunctionReturnNode returnType = FunctionReturnNode.parse(tokens);

        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("{")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        FBodyNode funcBody = FBodyNode.parse(tokens);

        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("}")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        return new FunctionDefNode(funcName, parameters, returnType, funcBody);
    }

    @Override
    public String convertToJott() {
        String s = "Def ";
        s += this.funcName.convertToJott();
        s += "[";
        s += this.parameters.convertToJott();
        s += "]:";
        s += this.returnType.convertToJott();
        s += "{";
        s += this.funcBody.convertToJott();
        s += "}";
        return s;
    }

    @Override
    public boolean validateTree() {
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

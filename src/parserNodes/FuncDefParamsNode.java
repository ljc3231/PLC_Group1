package parserNodes;
import provided.*;

import java.util.ArrayList;

public class FuncDefParamsNode implements JottTree {
    private final boolean paramsExist;
    private final IdNode name;
    private final TypeNode type;
    private final ArrayList<FuncDefParamsTNode> params;

    public FuncDefParamsNode() {
        this.paramsExist = false;
        this.name = null;
        this.type = null;
        this.params = null;
    }

    public FuncDefParamsNode(IdNode n, TypeNode t, ArrayList<FuncDefParamsTNode> p) {
        paramsExist = true;
        this.name = n;
        this.type = t;
        this.params = p;
    }

    public static FuncDefParamsNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (tokens.get(0).getToken().equals("]")) {
            return new FuncDefParamsNode();
        }

        IdNode name = IdNode.parse(tokens);

        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(0).getToken().equals(":")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        TypeNode type = TypeNode.parse(tokens);

        ArrayList<FuncDefParamsTNode> params = new ArrayList<>();
        while (!tokens.get(0).getToken().equals("]")) {
            params.add(FuncDefParamsTNode.parse(tokens));
        }

        return new FuncDefParamsNode(name, type, params);
    }

    @Override
    public String convertToJott() {
        if (!this.paramsExist) {
            return "";
        }

        String s = this.name.convertToJott();
        s += ":";
        s += this.type.convertToJott();
        for (FuncDefParamsTNode p : this.params) {
            s += p.convertToJott();
        }
        return s;
    }

    @Override
    public boolean validateTree() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

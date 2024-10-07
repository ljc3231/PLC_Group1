package parserNodes;
import provided.*;

import java.util.ArrayList;

public class FuncDefParamsTNode implements JottTree {
    private final IdNode name;
    private final TypeNode type;

    public FuncDefParamsTNode(IdNode n, TypeNode t) {
        this.name = n;
        this.type = t;
    }

    public static FuncDefParamsTNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(0).getToken().equals(",")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

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

        return new FuncDefParamsTNode(name, type);
    }

    @Override
    public String convertToJott() {
        String s = ",";
        s += this.name.convertToJott();
        s += ":";
        s += this.type.convertToJott();
        return s;
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

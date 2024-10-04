package parserNodes;
import provided.*;

import java.util.ArrayList;

public class FunctionReturnNode implements JottTree{
    private final TypeNode returnType;

    public FunctionReturnNode(TypeNode rT) {
        this.returnType = rT;
    }

    public static FunctionReturnNode parse(ArrayList<Token> tokens) {
        if (tokens.get(0).getToken().equals("Void")) {
            tokens.remove(0);
            return new FunctionReturnNode(null);
        }

        return new FunctionReturnNode(TypeNode.parse(tokens));
    }

    @Override
    public String convertToJott() {
        if (returnType == null) {
            return "Void";
        }

        return this.returnType.convertToJott();
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

package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class FunctionReturnNode implements JottTree{
    private final TypeNode RETURNTYPE;

    public FunctionReturnNode(TypeNode rT) {
        this.RETURNTYPE = rT;
    }

    public static FunctionReturnNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("return type");
        }
        if (tokens.get(0).getToken().equals("Void")) {
            tokens.remove(0);
            return new FunctionReturnNode(null);
        }

        return new FunctionReturnNode(TypeNode.parse(tokens));
    }

    @Override
    public String convertToJott() {
        if (RETURNTYPE == null) {
            return "Void";
        }

        return this.RETURNTYPE.convertToJott();
    }

    @Override
    public boolean validateTree() {
        if (RETURNTYPE == null) {
            return true;
        }

        return this.RETURNTYPE.validateTree();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

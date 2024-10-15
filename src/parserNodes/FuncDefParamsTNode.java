package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class FuncDefParamsTNode implements JottTree {
    private final static String FILENAME = "FuncDefParamsTNode";
    private final IdNode name;
    private final TypeNode type;

    public FuncDefParamsTNode(IdNode n, TypeNode t) {
        this.name = n;
        this.type = t;
    }

    public static FuncDefParamsTNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        JottTree.tryTerminal(tokens, ",", FILENAME);

        IdNode name = IdNode.parse(tokens);

        JottTree.tryTerminal(tokens, ":", FILENAME);

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

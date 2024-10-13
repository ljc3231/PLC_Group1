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
        if (tokens.isEmpty()) {
            throw new EndOfFileException("\",\"");
        }
        String t = tokens.get(1).getToken();
        if (!t.equals(",")) {
            throw new JottException(FILENAME, "Expected \",\", instead recieved \"" + t + "\"");
        }
        tokens.remove(0);

        IdNode name = IdNode.parse(tokens);

        if (tokens.isEmpty()) {
            throw new EndOfFileException("\":\"");
        }
        t = tokens.get(1).getToken();
        if (!t.equals(":")) {
            throw new JottException(FILENAME, "Expected \":\", instead recieved \"" + t + "\"");
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

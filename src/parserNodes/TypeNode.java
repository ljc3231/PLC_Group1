package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class TypeNode implements JottTree {
    private final String type;

    public TypeNode(String s) {
        this.type = s;
    }
    public static TypeNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("Type");
        }

        String s = tokens.get(0).getToken();
        if (!(s.equals("Double") || s.equals("Integer") || s.equals("String") || s.equals("Boolean"))) {
            throw new JottException("TypeNode", "expected type, instead recieved \"" + s + "\"", tokens.get(0).getLineNum());
        }
        tokens.remove(0);
        return new TypeNode(s);
    }

    @Override
    public String convertToJott() {
        return this.type;
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

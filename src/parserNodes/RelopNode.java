package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class RelopNode implements JottTree {
    private final static String FILENAME = "RelopNode";
    private final Token relop;

    public RelopNode(Token t) {
        this.relop = t;
    }

    public static RelopNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("relop");
        }
        Token t = tokens.get(0);
        if (!t.getTokenType().equals(TokenType.REL_OP)) {
            throw new JottException(true, FILENAME, "Expected relop, instead recieved \"" + t.getToken() + "\"", t.getLineNum());
        }
        tokens.remove(0);
        return new RelopNode(t);
    }

    @Override
    public String convertToJott() {
        return this.relop.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

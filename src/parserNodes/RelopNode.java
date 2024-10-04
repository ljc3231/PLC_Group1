package parserNodes;
import java.util.ArrayList;
import provided.*;

public class RelopNode implements JottTree {
    private final Token relop;

    public RelopNode(Token t) {
        this.relop = t;
    }

    public static RelopNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        Token t = tokens.get(0);
        if (!t.getTokenType().equals(TokenType.REL_OP)) {
            System.err.println("implement error");
            return null;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

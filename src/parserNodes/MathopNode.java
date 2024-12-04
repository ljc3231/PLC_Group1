package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class MathopNode implements JottTree{
    private final String mathOp;

    MathopNode(String s) {
        this.mathOp = s;
    }

    public static MathopNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("mathop");
        }
        
        Token t = tokens.get(0);

        if (!t.getTokenType().equals(TokenType.MATH_OP)) {
            throw new JottException(true, "MathopNode", "Expected mathop, instead recieved \"" + t.getToken() + "\".", t.getLineNum());
        }
        tokens.remove(0);
        return new MathopNode(t.getToken());
    }

    @Override
    public String convertToJott() {
        return this.mathOp;
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public String execute() {
        return null;
    }
}

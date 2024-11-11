package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public interface ExpressionNode extends JottTree{

    public static final String FILENAME = "ExpressionNode";
    
    public String getExprType() throws JottException;

    public static ExpressionNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {

        if(tokens.isEmpty()){
            throw new EndOfFileException("ExprNode");
        }

        Token t = tokens.get(0);
        String s = tokens.get(0).getToken();


        if(s.equals("True") || s.equals("False")){
            return BoolNode.parse(tokens);
        }

        if(t.getTokenType().equals(TokenType.STRING)){
            return StringLiteralNode.parse(tokens);
        }

        if (tokens.size() < 1) {
            throw new EndOfFileException("operand");
        }

        OperandNode o = OperandNode.parse(tokens);
        
        if(tokens.get(0).getTokenType().equals(TokenType.MATH_OP)){

            return OperandMathopOperand.parse(tokens,o);
        }
        if(tokens.get(0).getTokenType().equals(TokenType.REL_OP)){
            return OperandRelopOperand.parse(tokens, o);
        }

        return o;
    }
}

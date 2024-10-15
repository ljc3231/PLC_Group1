package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;

public interface ExpressionNode extends JottTree{

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

        try{
            OperandNode.parse(tokens);
        }
        catch(Exception e) {
            throw new JottException("ExprNode", "Expected operand, instead got " + tokens.get(0).getTokenType(), tokens.get(0).getLineNum());
        }

        if(!(tokens.get(1).getTokenType().equals(TokenType.MATH_OP) || tokens.get(1).getTokenType().equals(TokenType.REL_OP))){
            //ONLY operand
            return OperandNode.parse(tokens);

        }  
        
        //is EITHER < operand > < relop > < operand > | < operand > < mathop > < operand >
        
        if(tokens.get(1).getTokenType().equals(TokenType.MATH_OP)){
            return OperandMathopOperand.parse(tokens);
        }
        else{
            if(tokens.get(1).getTokenType().equals(TokenType.REL_OP)){
                return OperandRelopOperand.parse(tokens);
            }
        }

        throw new JottException("ExprNode", "Expected expression, instead got " + tokens.get(0).getTokenType(), tokens.get(0).getLineNum());
    }

}

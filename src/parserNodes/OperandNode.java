package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;

public interface OperandNode extends ExpressionNode {

    //literally the only function in this class
    //   < operand > -> <id > | <num > | < func_call > | -< num >
    public static OperandNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("Operand");
        }

        Token token = tokens.get(0);

        //Check if ID
        if(token.getTokenType().equals(TokenType.ID_KEYWORD)){
            return IdNode.parse(tokens);
        }


        //Check if NUM or neg #
        if(token.getTokenType().equals(TokenType.NUMBER) || token.getToken.equals("-")){
            return NumberNode.parse(tokens);
        }


        //Check in func call
        if(token.getTokenType().equals(TokenType.FC_HEADER)){
            return FuncCallNode.parse(tokens);
        }
    
        throw new JottException("OperandNode", "Expected Number, FC_HEADER, ID_KEYWORD, or OPERAND. Instead recieved " + token.getTokenType(), tokens.get(0).getLineNum());
    }
    
}

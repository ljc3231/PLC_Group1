package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;

public interface OperandNode extends JottTree {

    //literally the only function in this class
    //   < operand > -> <id > | <num > | < func_call > | -< num >
    public static OperandNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("Operand");
        }

        Token token = tokens.get(0);

        //Check if ID
        if(token.getTokenType.equals("ID_KEYWORD")){
            return IdNode.parse(tokens);
        }


        //Check if NUM
        if(token.getTokenType.equals("NUMBER")){
            return NumberNode.parse(tokens);
        }


        //Check in func call
        if(token.getTokenType.equals("FC_HEADER")){
            return FuncCallNode.parse(tokens);
        }


        //Check if neg num
        if(token.getToken.equals("-")){  
            tokens.remove(0);
            token = tokens.get(0);
            if(token.getToken.equals("NUMBER")){
                return NumberNode.parse(tokens);
            }
        }
    
        throw new JottException("OperandNode", "Expected Number, FC_HEADER, ID_KEYWORD, or OPERAND. Instead recieved " + token.getTokenType(), tokens.get(0).getLineNum());


    }
    
}

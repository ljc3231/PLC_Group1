package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;


public class OperandMathopOperand implements ExpressionNode{

    Arraylist<tokens> list;

    public OperandMathopOperand(Arraylist<tokens> l){
        this.list = l;
    }
    

    public static OperandMathopOperand parse(ArrayList<tokens> tokens) throws JottException, EndOfFileException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandMathopOperand");
        }


        //check if first is operand
        OperandNode.parse(tokens);

        Arraylist<tokens> holder;
        
        holder.add(tokens.get(0));
        tokens.remove(0);

        
        //check if next is math op
        if(!tokens.get(0).getTokenType().equals(TokenType.MATH_OP)){
            throw new JottException("OperandMathopOperand", "Expected MathOp, instead recieved " + tokens.get(0).getTokenType(), tokens.get(0).getLineNum());
        }

        holder.add(tokens.get(0));
        tokens.remove(0);


        //check if last is operand
        OperandNode.parse(tokens);
        

        holder.add(tokens.get(0));
        tokens.remove(0);

        return new OperandMathopOperand(holder);

    }

    @Override
    public String convertToJott() {
        return this.list.get(0).convertToJott() + this.list.get(1).convertToJott() + this.list.get(2).convertToJott();
    }

    @Override
    public boolean validateTree() {
        return null;
    }

    @Override
    public void execute() {

    }

}

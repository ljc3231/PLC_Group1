package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;


public class OperandRelopOperand implements ExpressionNode{

    Arraylist<tokens> list;

    public OperandRelopOperand(Arraylist<tokens> l){
        this.list = l;
    }
    

    public static OperandReloppOperand parse(ArrayList<tokens> tokens) throws JottException, EndOfFileException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandRelopOperand");
        }


        //check if first is operand
        OperandNode.parse(tokens);

        Arraylist<tokens> holder = new ArrayList<>();
        
        holder.add(tokens.get(0));
        tokens.remove(0);

        
        RelopNode.parse(tokens);

        holder.add(tokens.get(0));
        tokens.remove(0);


        //check if last is operand
        OperandNode.parse(tokens);
        

        holder.add(tokens.get(0));
        tokens.remove(0);

        return new OperandRelopOperand(holder);

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

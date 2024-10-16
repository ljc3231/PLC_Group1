package parserNodes;
import exceptionFiles.*;
import provided.*;

import java.util.ArrayList;


public class OperandRelopOperand implements ExpressionNode{
    private final OperandNode op1;
    private final OperandNode op2;
    private final RelopNode relOp;

    public OperandRelopOperand(OperandNode op1, RelopNode relOp, OperandNode op2){
        this.op1 = op1;
        this.relOp = relOp;
        this.op2 = op2;
    }


    public static OperandRelopOperand parse(ArrayList<Token> tokens, OperandNode op1) throws JottException, EndOfFileException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandRelopOperand");
        }


        //check if next is math op
        RelopNode relOp = RelopNode.parse(tokens);

        //check if last is operand
        OperandNode op2  = OperandNode.parse(tokens);

        return new OperandRelopOperand(op1, relOp , op2);

    }

    @Override
    public String convertToJott() {
        return this.op1.convertToJott() + this.relOp.convertToJott() + this.op2.convertToJott();
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

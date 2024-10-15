package parserNodes;
import exceptionFiles.*;
import provided.*;

import java.util.ArrayList;


public class OperandMathopOperand implements ExpressionNode{
    private final OperandNode op1;
    private final OperandNode op2;
    private final MathopNode mathOp;

    public OperandMathopOperand(OperandNode op1, MathopNode mathOp, OperandNode op2){
        this.op1 = op1;
        this.mathOp = mathOp;
        this.op2 = op2;
    }
    

    public static OperandMathopOperand parse(ArrayList<Token> tokens) throws JottException, EndOfFileException{

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandMathopOperand");
        }


        //check if first is operand
        OperandNode op1 = OperandNode.parse(tokens);

        //check if next is math op
        MathopNode mathOp = MathopNode.parse(tokens);

        //check if last is operand
        OperandNode op2  = OperandNode.parse(tokens);

        return new OperandMathopOperand(op1, mathOp, op2);

    }

    @Override
    public String convertToJott() {
        return this.op1.convertToJott() + this.mathOp.convertToJott() + this.op2.convertToJott();
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

package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;


public class OperandMathopOperand implements ExpressionNode{
    private final OperandNode op1;
    private final OperandNode op2;
    private final MathopNode mathOp;

    public OperandMathopOperand(OperandNode op1, MathopNode mathOp, OperandNode op2){
        this.op1 = op1;
        this.mathOp = mathOp;
        this.op2 = op2;
    }
    

    public static OperandMathopOperand parse(ArrayList<Token> tokens, OperandNode op1) throws JottException, EndOfFileException{
        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandMathopOperand");
        }

        int lineNum = tokens.get(0).getLineNum();

        //check if next is math op
        MathopNode mathOp = MathopNode.parse(tokens);

        //check if last is operand
        OperandNode op2  = OperandNode.parse(tokens);

        String op1Type = op1.getExprType().toLowerCase();
        String op2Type = op2.getExprType().toLowerCase();

        if (op1Type.equals("string") || op2Type.equals("string") || op1Type.equals("boolean") || op2Type.equals("boolean")) {
            throw new JottException(false, FILENAME, "MathOp \"" + mathOp.convertToJott() + "\" can only take operands of type \"int\" or \"double\"." , lineNum);
        }

        if (!op1Type.equals(op2Type)) {
            throw new JottException(false, FILENAME, "MathOp \"" + mathOp.convertToJott() + "\" must take 2 operands of the same type." , lineNum);
        }

        return new OperandMathopOperand(op1, mathOp, op2);

    }

    @Override
    public String convertToJott() {
        return this.op1.convertToJott() + this.mathOp.convertToJott() + this.op2.convertToJott();
    }

    @Override
    public boolean validateTree() {
        return this.op1.validateTree() && this.mathOp.validateTree() && this.op2.validateTree();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public String getExprType() {
        return this.op1.getExprType();
    }

    @Override
    public void setExprType(String type) {}

}

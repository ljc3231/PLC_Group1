package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;


public class OperandRelopOperand implements ExpressionNode{
    private final OperandNode op1;
    private final OperandNode op2;
    private final RelopNode relOp;
    private String exprType;

    public OperandRelopOperand(OperandNode op1, RelopNode relOp, OperandNode op2){
        this.op1 = op1;
        this.relOp = relOp;
        this.op2 = op2;
    }


    public static OperandRelopOperand parse(ArrayList<Token> tokens, OperandNode op1) throws JottException, EndOfFileException {

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandRelopOperand");
        }

        //check if next is math op
        RelopNode relOp = RelopNode.parse(tokens);

        //check if last is operand
        OperandNode op2  = OperandNode.parse(tokens);

        String op1Type = op1.getExprType();
        String op2Type = op2.getExprType();

        if (op1Type.equals("String") || op2Type.equals("String")) {
            throw new JottException(true, "OperandRelopOperand", "Variables of type String are not valid for relational operations", tokens.get(0).getLineNum());
        } else if ( (op1Type.equals("bool") || op2Type.equals("bool"))
                && (relOp.convertToJott().contains(">") || relOp.convertToJott().contains("<"))) {
            throw new JottException(true, "OperandRelopOperand", "Variables of type Boolean can not be compared with >, <, >=, or <=", tokens.get(0).getLineNum());
        } else if (!(op1Type.equals(op2Type))) {
            throw new JottException(true, "OperandRelopOperand", "Variables must be of the same type for relational operations", tokens.get(0).getLineNum());
        }

        return new OperandRelopOperand(op1, relOp , op2);

    }


    @Override
    public String convertToJott() {
        return this.op1.convertToJott() + this.relOp.convertToJott() + this.op2.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // Validate the left operand
        boolean isOp1Valid = op1.validateTree();

        // Validate the right operand
        boolean isOp2Valid = op2.validateTree();

        // Validate the relOP
        boolean isRelOpValid = relOp.validateTree();

        return isOp1Valid && isOp2Valid && isRelOpValid;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public String getExprType() {
        return exprType;
    }
}

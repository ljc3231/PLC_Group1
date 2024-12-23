package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;
import symbolTable.SymbolTable;


public class OperandRelopOperand implements ExpressionNode{
    private final String source = "OperandRelopOperand";
    private final OperandNode op1;
    private final OperandNode op2;
    private final RelopNode relOp;
    private final String exprType;
    private final int lineNum;

    public OperandRelopOperand(OperandNode op1, RelopNode relOp, OperandNode op2, int lN){
        this.op1 = op1;
        this.relOp = relOp;
        this.op2 = op2;
        this.exprType = "Boolean";
        this.lineNum = lN;
    }

    public static OperandRelopOperand parse(ArrayList<Token> tokens, OperandNode op1) throws JottException, EndOfFileException {

        if(tokens.isEmpty()){
            throw new EndOfFileException("OperandRelopOperand");
        }

        int lineNum = tokens.get(0).getLineNum();

        //check if next is math op
        RelopNode relOp = RelopNode.parse(tokens);

        //check if last is operand
        OperandNode op2  = OperandNode.parse(tokens);

        String op1Type = op1.getExprType();
        String op2Type = op2.getExprType();

        if (op1Type.equals("String") || op2Type.equals("String")) {
            throw new JottException(false, "OperandRelopOperand", "Variables of type String are not valid for relational operations", tokens.get(0).getLineNum());
        } else if ( (op1Type.equals("Boolean") || op2Type.equals("Boolean"))
                && (relOp.convertToJott().contains(">") || relOp.convertToJott().contains("<"))) {
            throw new JottException(false, "OperandRelopOperand", "Variables of type Boolean can not be compared with >, <, >=, or <=", tokens.get(0).getLineNum());
        } else if (!(op1Type.equals(op2Type))) {
            throw new JottException(false, "OperandRelopOperand", "Variables must be of the same type for relational operations", tokens.get(0).getLineNum());
        }

        return new OperandRelopOperand(op1, relOp , op2, lineNum);
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
    public String execute() throws JottException {
        String op1Val = op1.execute();
        String op2Val = op2.execute();
        if (op1.getExprType().equals("Boolean")) {
            if (op1Val.equals(op2Val)) {
                return "True";
            }
            return "False";
        }

        Double op1D = Double.valueOf(op1Val);
        Double op2D = Double.valueOf(op2Val);
        String relOpS = relOp.convertToJott();

        if (relOpS.equals("<")) {
            if (op1D < op2D) {
                return "True";
            }
            return "False";
        }
        if (relOpS.equals("<=")) {
            if (op1D <= op2D) {
                return "True";
            }
            return "False";
        }
        if (relOpS.equals(">")) {
            if (op1D > op2D) {
                return "True";
            }
            return "False";
        }
        if (relOpS.equals(">=")) {
            if (op1D >= op2D) {
                return "True";
            }
            return "False";
        }
        if (op1D == op2D) {
            return "True";
        }
        return "False";
    }

    @Override
    public String getExprType() {
        return exprType;
    }
}

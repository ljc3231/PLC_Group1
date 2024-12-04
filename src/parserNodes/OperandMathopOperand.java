package parserNodes;
import JottMain.Jott;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;


public class OperandMathopOperand implements ExpressionNode{
    private final OperandNode op1;
    private final OperandNode op2;
    private final MathopNode mathOp;
    private final int lineNumber;

    public OperandMathopOperand(OperandNode op1, MathopNode mathOp, OperandNode op2, int lineNum){
        this.op1 = op1;
        this.mathOp = mathOp;
        this.op2 = op2;
        this.lineNumber = lineNum;
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

        if (mathOp.convertToJott().equals("/") && Double.parseDouble(op2.convertToJott()) == 0) {
            throw new JottException(false, FILENAME, "Division by 0" , lineNum);
        }

        return new OperandMathopOperand(op1, mathOp, op2, lineNum);

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
    public String execute() throws JottException {
        // Execute and evaluate operands
        String op1Value = op1.execute();
        String op2Value = op2.execute();

        // Determine the type of the operands
        String exprType = op1.getExprType().toLowerCase();
        int lineNum = lineNumber;

        double doubleResult;
        int intResult;

        try {
            // Perform the math operation based on type
            switch (mathOp.convertToJott()) {
                case "+":
                    if (exprType.equals("integer")) {
                        intResult = Integer.parseInt(op1Value) + Integer.parseInt(op2Value);
                        return Integer.toString(intResult);
                    } else {
                        doubleResult = Double.parseDouble(op1Value) + Double.parseDouble(op2Value);
                        return Double.toString(doubleResult);
                    }
                case "-":
                    if (exprType.equals("integer")) {
                        intResult = Integer.parseInt(op1Value) - Integer.parseInt(op2Value);
                        return Integer.toString(intResult);
                    } else {
                        doubleResult = Double.parseDouble(op1Value) - Double.parseDouble(op2Value);
                        return Double.toString(doubleResult);
                    }
                case "*":
                    if (exprType.equals("integer")) {
                        intResult = Integer.parseInt(op1Value) * Integer.parseInt(op2Value);
                        return Integer.toString(intResult);
                    } else {
                        doubleResult = Double.parseDouble(op1Value) * Double.parseDouble(op2Value);
                        return Double.toString(doubleResult);
                    }
                case "/":
                    if (exprType.equals("integer")) {
                        int denominator = Integer.parseInt(op2Value);
                        if (denominator == 0) {
                            throw new JottException(false, "OperandMathopOperand", "Division by zero", lineNum);
                        }
                        intResult = Integer.parseInt(op1Value) / denominator;
                        return Integer.toString(intResult);
                    } else {
                        double denominator = Double.parseDouble(op2Value);
                        if (denominator == 0.0) {
                            throw new JottException(false, "OperandMathopOperand", "Division by zero", lineNum);
                        }
                        doubleResult = Double.parseDouble(op1Value) / denominator;
                        return Double.toString(doubleResult);
                    }
            }
        } catch (NumberFormatException e) {
            throw new JottException(false, "OperandMathopOperand", "Invalid number format in operands", lineNum);
        }
        return null;
    }


    @Override
    public String getExprType() throws JottException {
        return this.op1.getExprType();
    }
}

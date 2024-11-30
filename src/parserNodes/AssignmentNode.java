package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.*;
import provided.*;
import symbolTable.SymbolTable;

public class AssignmentNode implements BodyStatementNode, ParseTerminal {
    private final IdNode id;
    private final JottTree expression;
    public final static String FILENAME = "AssignmentNode";

    public AssignmentNode(IdNode id, JottTree expr) {
        this.id = id;
        this.expression = expr;
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        // Parse IdNode
        IdNode id = IdNode.parse(tokens);
        int lineNum = tokens.get(0).getLineNum();
        id.findExprType(false, tokens.get(0).getLineNum());

        // Check for ASSIGN token
        ParseTerminal.parseTerminal(tokens, "=", FILENAME);

        // Parse ExpressionNode
        ExpressionNode expression = ExpressionNode.parse(tokens);

        // Check for Semicolon
        ParseTerminal.parseTerminal(tokens, ";", FILENAME);

        // Get id type
        Map<String, Map<String, ArrayList<String>>> varMap = SymbolTable.getVarMap();

        List<String> variable = SymbolTable.getVariable(id.convertToJott(), FILENAME, lineNum);

        String idType = variable.get(0);

        // Get Expression Type
        String exprType = expression.getExprType();
//        if (expression instanceof OperandNode || expression instanceof ExpressionNode) {
//            exprType = ((OperandNode) expression).getExprType();
//        }

        // Check if the types are compatible
        if (idType == null || exprType == null || !idType.equals(exprType)) {
            throw new JottException(false, FILENAME, "Type mismatch in assignment. Expected " + idType + " but found " + exprType, tokens.get(0).getLineNum());
        }
        // Update the variable value in the symbol table
        try {
            SymbolTable.updateVariable(id.convertToJott(), expression.convertToJott());
        } catch (Exception e) {
            throw new JottException(false, FILENAME, "Error updating variable '" + id.convertToJott() + "': " + e.getMessage(), tokens.get(0).getLineNum());
        }
        return new AssignmentNode(id, expression);
    }

    @Override
    public String convertToJott() {
        return id.convertToJott() + " = " + expression.convertToJott() + ";";
    }

    @Override
    public boolean validateTree() {
        boolean isIdValid = id.validateTree();
        boolean isExpressionValid = expression != null && expression.validateTree();
        return isIdValid && isExpressionValid;
    }

    @Override
    public String execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean validReturn() {
        return false;
    }
}

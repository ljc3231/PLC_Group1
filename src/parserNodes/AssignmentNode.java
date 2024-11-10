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

        // Check for ASSIGN token
        ParseTerminal.parseTerminal(tokens, "=", FILENAME);

        // Parse ExpressionNode
        JottTree expression = ExpressionNode.parse(tokens);

        // Check for Semicolon
        ParseTerminal.parseTerminal(tokens, ";", FILENAME);


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

        // Get the type of the id from the symbol table
        String scope = SymbolTable.getScope();
        Map<String, List<String>> variableMap = SymbolTable.getVariableMap(scope);
        String idType = variableMap.get(id.convertToJott()).get(0);

        // Check if the id is in the Symbol Table
        if (!variableMap.containsKey(id.convertToJott())) {
            System.err.println("Error: Variable '" + id.convertToJott() + "' not found in the current scope.");
            return false;
        }

        // Get Expression Type
        String exprType = null;
        if (expression instanceof OperandNode) {
            exprType = ((OperandNode) expression).getExprType();
        }

        // Check if the types are compatible
        if (idType == null || exprType == null || !idType.equals(exprType)) {
            System.err.println("Error: Type mismatch in assignment. Expected " + idType + " but found " + exprType);
            return false;
        }

        // Update the variable value in the symbol table
        try {
            SymbolTable.updateVariable(id.convertToJott(), expression.convertToJott());

        } catch (Exception e) {
            System.err.println("Error updating variable '" + id.convertToJott() + "': " + e.getMessage());
            return false;
        }

        return isIdValid && isExpressionValid;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean validReturn() {
        return false;
    }
}

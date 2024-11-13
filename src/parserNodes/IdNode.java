package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;
import symbolTable.SymbolTable;

public class IdNode implements OperandNode {
    private final String id;
    private String exprType;
    public final static String FILENAME = "IdNode";

    public IdNode(String id) {
        this.id = id;
        exprType = null;
    }

    @Override
    public String getExprType() {
        return exprType;
    }
    public void setExprType(String type) {
        exprType = type;
    }
    // if the id is already in symbol table, and type is unknown by caller
    public void findExprType(boolean isFunc, int lineNum) throws JottException {
        if(isFunc) {
            exprType = SymbolTable.getReturnType(id, FILENAME, lineNum);
        }
        else{
            exprType = SymbolTable.getVariable(id, FILENAME, lineNum).get(0);
        }
    }


    public static IdNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        Token currentToken = tokens.get(0);
        String id = currentToken.getToken();

        // Check if token is a ID_KEYWORD
        if (!currentToken.getTokenType().equals(TokenType.ID_KEYWORD)) {
            throw new JottException(false, FILENAME, "Expected: ID_KEYWORD, but got " + currentToken.getTokenType(), currentToken.getLineNum());
        }
        tokens.remove(0);

        if (!Character.isLowerCase(id.charAt(0))) {
            throw new JottException(false, FILENAME, "Uppercase ID first character", currentToken.getLineNum());
        }
        return new IdNode(id);
    }

    @Override
    public String convertToJott() {
        return id;
    }

    @Override
    public boolean validateTree() {
        return !id.isEmpty() && Character.isLowerCase(id.charAt(0));
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

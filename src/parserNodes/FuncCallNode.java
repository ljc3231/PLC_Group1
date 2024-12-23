package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.*;
import provided.*;
import symbolTable.SymbolTable;

public class FuncCallNode implements BodyStatementNode, OperandNode, ParseTerminal {
    private final IdNode functionName;
    private final ParamsNode params;
    private final String ReturnType; 
    private final boolean validReturn;
    public final static String FILENAME = "FuncCallNode";

    public FuncCallNode(IdNode functionName, ParamsNode params, String retType, boolean validRet) {
        this.functionName = functionName;
        this.params = params;
        this.ReturnType = retType;
        this.validReturn = validRet;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        // Check if tokens is empty
        if (tokens.isEmpty()) {
            throw new EndOfFileException(FILENAME);
        }

        int lineNum = tokens.get(0).getLineNum();

        // FC_HEADER token
        ParseTerminal.parseTerminal(tokens, "::", FILENAME);

        // Parse IdNode
        IdNode functionName = IdNode.parse(tokens);

        // Left Bracket check
        ParseTerminal.parseTerminal(tokens, "[", FILENAME);

        // Parse ParamsNode
        ParamsNode params = ParamsNode.parse(tokens);
        
        // Right Bracket check
        ParseTerminal.parseTerminal(tokens, "]", FILENAME);

        if (!SymbolTable.funcExists(functionName.convertToJott())) {
            throw new JottException(false, FILENAME, "Function does not exist or must be defined before it is used", lineNum);
        }

        if (!SymbolTable.isValidFunctionCall(functionName.convertToJott(), params.getTypes())) {
            throw new JottException(false, FILENAME, "Function expected different parameters than recieved", lineNum);
        }

        String retType = SymbolTable.getReturnType(functionName.convertToJott(), FILENAME, lineNum);
        functionName.setExprType(retType);
        
        return new FuncCallNode(functionName, params, retType, retType.equals(SymbolTable.getReturnType()));
    }

    @Override
    public String convertToJott() {
        return "::" + functionName.convertToJott() + "[" + params.convertToJott() + "]";
    }

    @Override
    public boolean validateTree() {
        if (!functionName.validateTree()) {
            return false;
        }
        
        if (!params.exists()) {
            return true;
        }
        
        return params.validateTree();
    }

    @Override
    public String execute() throws JottException {
        String scope = SymbolTable.getScope();
        String s = SymbolTable.executeFunction(functionName.convertToJott(), params.execute());
        SymbolTable.updateScope(scope);
        return s;
    }

    @Override
    public boolean validReturn() {
        return this.validReturn;
    }

    @Override
    public String getExprType() {
        return this.ReturnType;
    }
}

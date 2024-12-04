package parserNodes;
import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import java.util.Arrays;
import provided.*;
import symbolTable.*;

public class FunctionDefNode implements JottTree, ParseTerminal {
    public final static String FILENAME = "FunctionDefNode";
    private final IdNode FUNCNAME;
    private final FuncDefParamsNode PARAMS;
    private final FunctionReturnNode RETURNTYPE;
    private final FBodyNode FUNCBODY;

    public FunctionDefNode(IdNode fN, FuncDefParamsNode p, FunctionReturnNode rT, FBodyNode fB) {
        this.FUNCNAME = fN;
        this.PARAMS = p;
        this.RETURNTYPE = rT;
        this.FUNCBODY = fB;
    }

    public String GetFuncName() {
        return FUNCNAME.convertToJott();
    }

    public static FunctionDefNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        int lineNum = tokens.get(0).getLineNum();

        ParseTerminal.parseTerminal(tokens, "Def", FILENAME);

        IdNode funcName = IdNode.parse(tokens);

        SymbolTable.updateScope(funcName.convertToJott());

        ParseTerminal.parseTerminal(tokens, "[", FILENAME);

        FuncDefParamsNode parameters = FuncDefParamsNode.parse(tokens);
        
        ParseTerminal.parseTerminal(tokens, "]", FILENAME);
        ParseTerminal.parseTerminal(tokens, ":", FILENAME);
        
        FunctionReturnNode returnType = FunctionReturnNode.parse(tokens);
        funcName.setExprType(returnType.convertToJott());

        if (funcName.convertToJott().equals("main") && !returnType.convertToJott().equals("Void")) {
            throw new JottException(false, FILENAME, "Main function must be of return type Void", lineNum);
        }
        if (funcName.convertToJott().equals("main") && parameters.exists()) {
            throw new JottException(false, FILENAME, "Main function must not have parameters", lineNum);
        }

        addToSymTab(funcName, parameters, returnType, lineNum);

        ParseTerminal.parseTerminal(tokens, "{", FILENAME);

        FBodyNode funcBody = FBodyNode.parse(tokens);

        ParseTerminal.parseTerminal(tokens, "}", FILENAME);

        FunctionDefNode node = new FunctionDefNode(funcName, parameters, returnType, funcBody);

        SymbolTable.addFuncDef(funcName.convertToJott(), node);

        return node;
    }

    private static void addToSymTab(IdNode funcName, FuncDefParamsNode parameters, FunctionReturnNode returnType, int lineNum) throws JottException {
        ArrayList<String> params = new ArrayList<>();

        if (!parameters.convertToJott().equals("")) {
            String[] pArray = parameters.convertToJott().split(",");
            params.addAll(Arrays.asList(pArray));
        }

        SymbolTable.addFunction(funcName.convertToJott(), params, returnType.convertToJott(), FILENAME, lineNum);
    }

    @Override
    public String convertToJott() {
        String s = "Def ";
        s += this.FUNCNAME.convertToJott();
        s += "[";
        if (this.PARAMS.convertToJott() != null) {
            s += this.PARAMS.convertToJott();
        }
        s += "]:";
        s += this.RETURNTYPE.convertToJott();
        s += "{";
        s += this.FUNCBODY.convertToJott();
        s += "}";
        return s;
    }

    @Override
    public boolean validateTree() {
        if (!this.FUNCNAME.validateTree()) {
            return false;
        }
        if (!this.PARAMS.validateTree()) {
            return false;
        }
        if (!this.RETURNTYPE.validateTree()) {
            return false;
        }
        
        return this.FUNCBODY.validateTree();
    }

    @Override
    public String execute() throws JottException {
        FUNCBODY.execute();
        return null;
    }
}

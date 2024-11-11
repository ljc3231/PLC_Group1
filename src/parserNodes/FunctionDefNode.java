package parserNodes;
import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
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

    public static FunctionDefNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        int lineNum = tokens.get(0).getLineNum();

        ParseTerminal.parseTerminal(tokens, "Def", FILENAME);

        IdNode funcName = IdNode.parse(tokens, true);

        ParseTerminal.parseTerminal(tokens, "[", FILENAME);

        FuncDefParamsNode parameters = FuncDefParamsNode.parse(tokens);
        
        ParseTerminal.parseTerminal(tokens, "]", FILENAME);
        ParseTerminal.parseTerminal(tokens, ":", FILENAME);
        
        FunctionReturnNode returnType = FunctionReturnNode.parse(tokens);

        addToSymTab(funcName, parameters, returnType, lineNum);

        ParseTerminal.parseTerminal(tokens, "{", FILENAME);

        FBodyNode funcBody = FBodyNode.parse(tokens);

        ParseTerminal.parseTerminal(tokens, "}", FILENAME);

        return new FunctionDefNode(funcName, parameters, returnType, funcBody);
    }

    private static void addToSymTab(IdNode funcName, FuncDefParamsNode parameters, FunctionReturnNode returnType, int lineNum) throws JottException {
        ArrayList<String> params = new ArrayList<>();

        if (parameters.convertToJott() != null) {
            String[] pArray = parameters.convertToJott().split(",");

            for (String p : pArray) {
                params.add(p.substring(p.indexOf(":") + 1));
            }
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
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

package parserNodes;
import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;
import symbolTable.SymbolTable;

public class FuncDefParamsNode implements JottTree, ParseTerminal {
    public final static String FILENAME = "FuncDefParamsNode";
    private final boolean paramsExist;
    private final IdNode name;
    private final TypeNode type;
    private final ArrayList<FuncDefParamsTNode> params;

    public FuncDefParamsNode() {
        this.paramsExist = false;
        this.name = null;
        this.type = null;
        this.params = null;
    }

    public FuncDefParamsNode(IdNode n, TypeNode t, ArrayList<FuncDefParamsTNode> p) {
        this.paramsExist = true;
        this.name = n;
        this.type = t;
        this.params = p;
    }

    public static FuncDefParamsNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("function parameters");
        }

        int lineNum = tokens.get(0).getLineNum();

        if (tokens.get(0).getToken().equals("]")) {
            return new FuncDefParamsNode();
        }

        IdNode name = IdNode.parse(tokens);

        ParseTerminal.parseTerminal(tokens, ":", FILENAME);

        TypeNode type = TypeNode.parse(tokens);
        name.setExprType(type.convertToJott());

        SymbolTable.addVariable(name.convertToJott(), type.convertToJott(), lineNum);

        ArrayList<FuncDefParamsTNode> params = new ArrayList<>();

        while (!tokens.get(0).getToken().equals("]")) {
            params.add(FuncDefParamsTNode.parse(tokens));
        }


        return new FuncDefParamsNode(name, type, params);
    }

    @Override
    public String convertToJott() {
        if (!this.paramsExist) {
            return "";
        }

        String s = this.name.convertToJott();
        s += ":";
        s += this.type.convertToJott();
        for (FuncDefParamsTNode p : this.params) {
            s += p.convertToJott();
        }
        return s;
    }

    @Override
    public boolean validateTree() {
        if (!this.paramsExist) {
            return true;
        }
        if (!this.name.validateTree()) {
            return false;
        }
        if (!this.type.validateTree()) {
            return false;
        }

        for (FuncDefParamsTNode p : this.params) {
            if (!p.validateTree()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

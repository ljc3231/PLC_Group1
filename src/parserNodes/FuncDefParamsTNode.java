package parserNodes;
import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import provided.*;
import symbolTable.SymbolTable;

public class FuncDefParamsTNode implements JottTree, ParseTerminal {
    private final static String FILENAME = "FuncDefParamsTNode";
    private final IdNode name;
    private final TypeNode type;

    public FuncDefParamsTNode(IdNode n, TypeNode t) {
        this.name = n;
        this.type = t;
    }

    public static FuncDefParamsTNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        int lineNum = tokens.get(0).getLineNum();
        
        ParseTerminal.parseTerminal(tokens, ",", FILENAME);

        IdNode name = IdNode.parse(tokens);

        ParseTerminal.parseTerminal(tokens, ":", FILENAME);

        TypeNode type = TypeNode.parse(tokens);

        SymbolTable.addVariable(name.convertToJott(), type.convertToJott(), lineNum);

        return new FuncDefParamsTNode(name, type);
    }

    @Override
    public String convertToJott() {
        String s = ",";
        s += this.name.convertToJott();
        s += ":";
        s += this.type.convertToJott();
        return s;
    }

    @Override
    public boolean validateTree() {
        if (!this.name.validateTree()) {
            return false;
        }
        return this.type.validateTree();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

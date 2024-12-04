package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;
import symbolTable.SymbolTable;

public class ProgramNode implements JottTree{
    private final ArrayList<FunctionDefNode> functions;
    private static final String FILENAME = "ProgramNode";

    public ProgramNode(ArrayList<FunctionDefNode> f) {
        this.functions = f;
    }

    public static ProgramNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        SymbolTable.init();
        ArrayList<FunctionDefNode> functions = new ArrayList<>();
        boolean programExists = !tokens.isEmpty();

        while(!tokens.isEmpty()) {
            functions.add(FunctionDefNode.parse(tokens));
        }

        if (programExists && !SymbolTable.mainExists()) {
            throw new JottException(false, FILENAME, "Program must have a valid entry point (main function)", 0);
        }

        return new ProgramNode(functions);
    }

    @Override
    public String convertToJott() {
        String s = "";
        for (FunctionDefNode f : this.functions) {
            s += f.convertToJott() + "\n";
        }
        return s;
    }

    @Override
    public boolean validateTree() {
        for (FunctionDefNode f : functions) {
            if (!f.validateTree()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String execute() {
        for (FunctionDefNode f : this.functions) {
            if (f.GetFuncName().equals("main")) {
                SymbolTable.updateScope("main");
                f.execute();
                break;
            }
        }
        return null;
    }
}

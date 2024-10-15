package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public class FunctionDefNode implements JottTree {
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
        JottTree.tryTerminal(tokens, "Def", FILENAME);

        IdNode funcName = IdNode.parse(tokens);

        JottTree.tryTerminal(tokens, "[", FILENAME);

        FuncDefParamsNode parameters = FuncDefParamsNode.parse(tokens);
        
        JottTree.tryTerminal(tokens, "]", FILENAME);
        JottTree.tryTerminal(tokens, ":", FILENAME);

        FunctionReturnNode returnType = FunctionReturnNode.parse(tokens);

        JottTree.tryTerminal(tokens, "{", FILENAME);

        FBodyNode funcBody = FBodyNode.parse(tokens);

        JottTree.tryTerminal(tokens, "}", FILENAME);

        return new FunctionDefNode(funcName, parameters, returnType, funcBody);
    }

    @Override
    public String convertToJott() {
        String s = "Def ";
        s += this.FUNCNAME.convertToJott();
        s += "[";
        s += this.PARAMS.convertToJott();
        s += "]:";
        s += this.RETURNTYPE.convertToJott();
        s += "{";
        s += this.FUNCBODY.convertToJott();
        s += "}";
        return s;
    }

    @Override
    public boolean validateTree() {
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

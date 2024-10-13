package parserNodes;
import provided.*;
import exceptionFiles.*;

import java.util.ArrayList;

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
        tryToken(tokens, "Def");

        IdNode funcName = IdNode.parse(tokens);

        tryToken(tokens, "[");

        FuncDefParamsNode parameters = FuncDefParamsNode.parse(tokens);
        
        tryToken(tokens, "]");
        tryToken(tokens, ":");

        FunctionReturnNode returnType = FunctionReturnNode.parse(tokens);

        tryToken(tokens, "{");

        FBodyNode funcBody = FBodyNode.parse(tokens);

        tryToken(tokens, "}");

        return new FunctionDefNode(funcName, parameters, returnType, funcBody);
    }

    private static void tryToken(ArrayList<Token> tokens, String s) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException(s);
        }
        String t = tokens.get(1).getToken();
        if (!t.equals(s)) {
            throw new JottException(FILENAME, "Expected \"" + s + "\", instead recieved \"" + t + "\"");
        }
        tokens.remove(0);
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

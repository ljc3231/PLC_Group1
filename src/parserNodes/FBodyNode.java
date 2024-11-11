package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class FBodyNode implements JottTree {
    boolean hasVariableDeclaration;
    ArrayList<VarDecNode> varDecNodeList;
    BodyNode bodyNode;

    public static FBodyNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        //< f_body > -> < var_dec >⋆ < body >
        if(tokens.isEmpty()){
            throw new EndOfFileException("Function Body");
        }
        boolean vardec = true;
        ArrayList<VarDecNode> vdList = new ArrayList<>();

        // run until you can't add another variable declaration
        String t = tokens.get(0).getToken();
        while(t.equals("Integer") || t.equals("Double") || t.equals("String") || t.equals("Boolean")) {
            vdList.add(VarDecNode.parse(tokens));
            t = tokens.get(0).getToken();
        }

        if(vdList.isEmpty()) {
            vardec = false;
        }
        BodyNode b = BodyNode.parse(tokens);
        return new FBodyNode(vardec, vdList, b);
    }
    public FBodyNode(boolean hasVarDec, ArrayList<VarDecNode> vdList, BodyNode b) {
        hasVariableDeclaration = hasVarDec;
        varDecNodeList = vdList;
        bodyNode = b;
    }
    @Override
    public String convertToJott() {
        //< f_body > -> < var_dec >⋆ < body >
        String jott = "";
        if (hasVariableDeclaration) {
            for(VarDecNode v : varDecNodeList) {
                jott += v.convertToJott();
            }
            jott += " " + bodyNode.convertToJott();
            return jott;
        }
        return bodyNode.convertToJott();
    }

    @Override
    public boolean validateTree() {
        if (hasVariableDeclaration) {
            for (VarDecNode v : varDecNodeList) {
                if (!v.validateTree()) {
                    return false;
                }
            }
        }
        return bodyNode.validateTree();
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

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
        try {
            // run until you can't add another variable declaration
            while(true) {
                vdList.add(VarDecNode.parse(tokens));
            }
        } catch (JottException e) {
            // if error was thrown from Variable Declaration, means there is no variable declaration statement
            if (e.getSource().equals(VarDecNode.FILENAME)){
                if(vdList.isEmpty()) {
                    vardec = false;
                }
            }
            // else, pass the error upwards
            else {
                throw e;
            }
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
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

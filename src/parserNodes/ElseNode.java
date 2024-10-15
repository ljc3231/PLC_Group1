package parserNodes;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class ElseNode implements JottTree {
    private final BodyNode body;
    public ElseNode(){
        this.body = null;
    }
    public ElseNode(BodyNode body){
        this.body =  body;
    }

    public static ElseNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            EndOfFileException();
            return null;
        }

        if (tokens.get(0).getToken().equals("Else")){
            tokens.remove(0);
            if (tokens.get(0).getToken().equals("{")){
                tokens.remove(0);
                BodyNode body = BodyNode.parse(tokens);
                if (tokens.get(0).getToken().equals("}")){
                    tokens.remove(0);
                    return new ElseNode(body);
                }
            }
        }

        //return a null else node
        return new ElseNode();
    }

    @Override
    public String convertToJott() {
        if (this.body == null){
            return "";
        }
        String s = "Else";
        s += "{" + this.body.convertToJott() + "}";
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

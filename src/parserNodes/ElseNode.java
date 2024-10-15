package parserNodes;

import exceptionFiles.*;
import provided.*;
import java.util.ArrayList;

public class ElseNode implements JottTree {
    private final static String FILENAME = "ElseNode";
    private final BodyNode body;

    public ElseNode(){
        this.body = null;
    }
    public ElseNode(BodyNode body){
        this.body =  body;
    }

    public static ElseNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("}");
        }

        //return a null else node if not else
        if (!tokens.get(0).getToken().equals("Else")) {
            return new ElseNode();
        }
        tokens.remove(0);

        JottTree.tryTerminal(tokens, "{", FILENAME);

        BodyNode body = BodyNode.parse(tokens);

        JottTree.tryTerminal(tokens, "}", FILENAME);

        //return an else node
        return new ElseNode(body);
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

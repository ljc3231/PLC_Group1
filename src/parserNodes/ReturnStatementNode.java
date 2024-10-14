package parserNodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree{
    private final ExprNode exp;
    private final Boolean exists;

    public ReturnStatementNode(){
        this.exp = null;
        this.exists = false;
    }

    public ReturnStatementNode(ExprNode e, Boolean b){
        this.exp = e;
        this.exists = b;
    }


    public static ReturnStatementNode parse(ArrayList<Token> tokens) {

        if(tokens.isEmpty()){
            return new ReturnStatementNode();
        }
        

        String s = tokens.get(0).getToken();

        if(!(s.equals("Return"))){
            throw new JottException("ReturnStmtNode", "Expected Return, instead recieved \"" + s + "\"");
        }

        Bool exists = True;

        tokens.pop(0);

        //At this point, no return in token list, first token is the return expr

        Token t = tokens.get(0);


        ExprNode expr = ExprNode.parse(t);

        tokens.remove(0);        


        if(!(tokens.get(0).getToken().equals(";"))){
            throw new JottException("ReturnStmtNode", "Expected Semicolon, instead recieved \"" + tokens.get(0).getToken() + "\"");
        }

        tokens.remove(0);

        return new ReturnStatementNode(expr, exists);
    }

    @Override
    public String convertToJott() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    @Override
    public void execute() {

    }
}

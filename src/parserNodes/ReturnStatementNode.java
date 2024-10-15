package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree{
    private final ExpressionNode exp;
    private final Boolean exists;

    public ReturnStatementNode(){
        this.exp = null;
        this.exists = false;
    }

    public ReturnStatementNode(ExpressionNode e, Boolean b){
        this.exp = e;
        this.exists = b;
    }


    public static ReturnStatementNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {

        if(tokens.isEmpty()){
            return new ReturnStatementNode();
        }
        

        String s = tokens.get(0).getToken();

        if(!(s.equals("Return"))){
            throw new JottException("ReturnStmtNode", "Expected Return, instead recieved \"" + s + "\"", tokens.get(0).getLineNum());
        }

        boolean exists = true;

        tokens.remove(0);

        //At this point, no return in token list, first token is the return expr

        Token t = tokens.get(0);


        ExpressionNode expr = ExpressionNode.parse(tokens);

        tokens.remove(0);        


        if(!(tokens.get(0).getToken().equals(";"))){
            throw new JottException("ReturnStmtNode", "Expected Semicolon, instead recieved \"" + tokens.get(0).getToken() + "\"", tokens.get(0).getLineNum());
        }

        tokens.remove(0);

        return new ReturnStatementNode(expr, exists);
    }

    @Override
    public String convertToJott() {
        return "Return " + this.exp.convertToJott() + ';';
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    @Override
    public void execute() {

    }
}

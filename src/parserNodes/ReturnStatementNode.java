package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import helpers.ParseTerminal;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbolTable.SymbolTable;

public class ReturnStatementNode implements JottTree{
    private final ExpressionNode exp;
    private final Boolean exists;
    public static final String FILENAME = "ReturnStatementNode";

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
            throw new EndOfFileException("Return");
        }

        if(tokens.get(0).getTokenType() == TokenType.R_BRACE) {
            return new ReturnStatementNode();
        }

        ParseTerminal.parseTerminal(tokens, "Return", FILENAME);
        // String s = tokens.get(0).getToken();

        // if(!(s.equals("Return"))){
        //     throw new JottException(true, FILENAME, "Expected Return, instead recieved \"" + s + "\"", tokens.get(0).getLineNum());
        // }

        boolean exists = true;

        //At this point, no return in token list, first token is the return expr


        ExpressionNode expr = ExpressionNode.parse(tokens);

        //Get type of expr
        String thisType = expr.getExprType();

        //Get intended ret type (I added a function to get return type from symtab -Alex)
        String retType = SymbolTable.getReturnType();

        //Check if match
        if(!retType.toLowerCase().equals(thisType.toLowerCase())){
            throw new JottException(false, FILENAME, "Expected return type \"" + retType + "\", but instead received \"" + thisType + "\"", tokens.get(0).getLineNum());
        }

        ParseTerminal.parseTerminal(tokens, ";", FILENAME);
        // if(!(tokens.get(0).getToken().equals(";"))){
        //     throw new JottException(true, FILENAME, "Expected Semicolon, instead recieved \"" + tokens.get(0).getToken() + "\"", tokens.get(0).getLineNum());
        // }

        // tokens.remove(0);

        return new ReturnStatementNode(expr, exists);
    }

    public boolean exists() {
        return exists;
    }

    @Override
    public String convertToJott() {
        return this.exp == null? "" : "Return " + this.exp.convertToJott() + ';';
    }

    @Override
    public boolean validateTree() {
        return this.exp == null || this.exp.validateTree();
    }

    @Override
    public String execute() throws JottException{
        if(exists) {
            return exp.execute();
        }
        return null;
    }
}

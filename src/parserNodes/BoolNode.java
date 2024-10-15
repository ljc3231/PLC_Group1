package parserNodes;
import exceptionFiles.*;

import provided.*;
import java.util.ArrayList;

import javax.swing.JToggleButton;



public class BoolNode implements JottTree {
    private final String bool;

    public BoolNode(String s){
        this.bool = s;
    }

    public static BoolNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        

        if(tokens.isEmpty()){
            throw new EndOfFileException("Bool");
        }

        String s = token.get(0).getToken();

        if( ! (s.equals("True") || s.equals("False")) ){
            throw new JottException("BoolNode", "expected boolean, instead recieved \"" + s + "\"", tokens.get(0).getLineNum());
        }

        tokens.remove(0);


        return new BoolNode(s);
    }


    @Override
    public String convertToJott() {
        return this.bool;
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    @Override
    public void execute() {

    }
}

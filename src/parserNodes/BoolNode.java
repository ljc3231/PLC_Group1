package parserNodes;
import exceptionFiles.*;

import provided.*;
import java.util.ArrayList;

import javax.swing.JToggleButton;



public class BoolNode implements JottTree {
    private final Boolean bool;

    public BoolNode(String s){
        this.bool = s;
    }

    public static BoolNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        

        if(tokens.isEmpty()){
            throw new EndOfFileException("Bool");
        }

        String s = token.get(0).getToken();

        if( ! (s.equals("True") || s.equals("False")) ){
            throw new JottException("BoolNode", "expected boolean, instead recieved \"" + s + "\"");
        }

        tokens.remove(0);

        return new BoolNode(s);
    }
}

package parserNodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public interface OperandNode extends JottTree {

    //literally the only function in this class
    public static OperandNode parseOperand(ArrayList<Token> tokens){
        return null;    // TODO:needs implementation
    }
}

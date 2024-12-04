package parserNodes;
import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;



public class BoolNode implements ExpressionNode {
    private final String bool;

    public static final String FILENAME = "BoolNode";
    private String type = null;


    @Override
    public String getExprType(){
        return type;
    }

    public BoolNode(String s){
        this.bool = s;
        this.type = "Boolean";
    }

    public static BoolNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        

        if(tokens.isEmpty()){
            throw new EndOfFileException("Bool");
        }



        String s = tokens.get(0).getToken();

        if( ! (s.equals("True") || s.equals("False")) ){
            throw new JottException(true, FILENAME, "expected boolean, instead recieved \"" + s + "\"", tokens.get(0).getLineNum());
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
        return true;
    }

    @Override
    public String execute() {
        return this.bool;
    }
}

package parserNodes;
import exceptionFiles.*;

import provided.*;

import java.util.ArrayList;

public class ExpressionNode implements JottTree{
    private ArrayList<token> expression;

    public ExpressionNode(ArrayList<token> e){
        this.expression = e;
    }

    public static ExpressionNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {

        if(tokens.isEmpty()){
            throw new EndOfFileException("ExprNode");
        }

        Token t = tokens.get(0);
        String s = tokens.get(0).getToken();


        if(s.equals("True") || s.equals("False")){
            return BoolNode.parse(tokens);
        }

        if(t.getTokenType().equals("STRING")){
            return StringLiteralNode.parse(tokens);
        }

        ArrayList<tokens> exprList;

        if(t.getTokenType().equals("NUMBER")){
            exprList.add(t);
            tokens.remove(0);


            if(!(tokens.get(0).getTokenType.equals("REL_OP") || tokens.get(0).getTokenType.equals("MATH_OP"))){
                return new ExpressionNode(exprList);
            }

            exprList.add(tokens.get(0));
            tokens.remove(0);

            if(!(tokens.get(0).getTokenType().equals("NUMBER"))){
                throw new JottException("ExprNode", "Expected NUMBER, instead got " + tokens.get(0));
            }

            exprList.add(tokens.get(0));
            tokens.remove(0);

            return new ExpressionNode(exprList);
        }

        


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

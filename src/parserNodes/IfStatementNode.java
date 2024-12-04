package parserNodes;

import exceptionFiles.*;
import helpers.*;
import java.util.ArrayList;
import java.util.Objects;

import provided.*;

public class IfStatementNode implements BodyStatementNode, ParseTerminal {
    public final static String FILENAME = "IfStatementNode";
    private final ExpressionNode ifExpression;
    private final BodyNode body;
    private final ArrayList<ElseIfNode> elseIfs;
    private final ElseNode elseNode;

    public IfStatementNode(ExpressionNode ifExpression, BodyNode body, ArrayList<ElseIfNode> elseIfs, ElseNode elseNode) {
        this.ifExpression = ifExpression;
        this.body = body;
        this.elseIfs = elseIfs;
        this.elseNode = elseNode;
    }

    public static IfStatementNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        ParseTerminal.parseTerminal(tokens, "If", FILENAME);

        //Parsing [<expression>]
        ParseTerminal.parseTerminal(tokens, "[", FILENAME);
        ExpressionNode ifExpression = ExpressionNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "]", FILENAME);

        //Parsing {<body>}
        ParseTerminal.parseTerminal(tokens, "{", FILENAME);
        BodyNode body = BodyNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, "}", FILENAME);

        //Parsing <elseIfs>
        ArrayList<ElseIfNode> elseIfs = new ArrayList<>();
        while (tokens.get(0).getToken().equals("Elseif")){
                elseIfs.add(ElseIfNode.parse(tokens));
        }

        //Parsing <else>
        ElseNode elseNode = ElseNode.parse(tokens);

        return new IfStatementNode(ifExpression, body, elseIfs, elseNode);
    }

    @Override
    public String convertToJott() {
        StringBuilder s = new StringBuilder("If");
        s.append("[" + this.ifExpression.convertToJott() + "]");
        s.append("{" + this.body.convertToJott() + "}");
        for (ElseIfNode node:this.elseIfs){
            s.append(node.convertToJott());
        }
        s.append(this.elseNode.convertToJott());

        return s.toString();
    }

    @Override
    public boolean validateTree() {
        if(ifExpression.validateTree() && body.validateTree()){
            for (ElseIfNode node:elseIfs){
                if (!node.validateTree()){
                    return false;
                }
            }
            return elseNode.validateTree();
        }
        return false;
    }

    @Override
    public String execute() {
        if (ifExpression.execute().equals("True")) {
            return body.execute();
        }
        if (!elseIfs.isEmpty()){
            for(ElseIfNode elseIfNode: elseIfs){
                String result = elseIfNode.execute();
                if (!result.isEmpty()){
                    return result;
                }
            }
        }
        if (elseNode != null){
            return elseNode.execute();
        }
        return "";
    }

    @Override
    public boolean validReturn() {
        if (!this.elseNode.exists()) {
            return false;
        }

        if (!this.body.validReturn()) {
            return false;
        }

        for (ElseIfNode eIf : this.elseIfs) {
            if (!eIf.validReturn()) {
                return false;
            }
        }

        return this.elseNode.validReturn();
    }
}

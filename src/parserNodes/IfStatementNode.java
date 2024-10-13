package parserNodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class IfStatementNode implements BodyStatementNode {
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

    public static IfStatementNode parse(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            System.err.println("implement error");
            return null;
        }
        if (!tokens.get(1).getToken().equals("If")) {
            System.err.println("implement error");
            return null;
        }

        //Parsing [<expression>]
        tokens.remove(0);
        if (!tokens.get(1).getToken().equals("[")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);
        ExpressionNode ifExpression = ExpressionNode.parse(tokens);
        if (!tokens.get(1).getToken().equals("]")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        //Parsing {<body>}
        if (!tokens.get(1).getToken().equals("{")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);
        BodyNode body = BodyNode.parse(tokens);
        if (!tokens.get(1).getToken().equals("}")) {
            System.err.println("implement error");
            return null;
        }
        tokens.remove(0);

        //Parsing <elseIfs>
        ArrayList<ElseIfNode> elseIfs = new ArrayList<>();
        while (!tokens.isEmpty()){
            if (tokens.get(1).getToken().equals("ElseIf")) {
                elseIfs.add(ElseIfNode.parse(tokens));
            }
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
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

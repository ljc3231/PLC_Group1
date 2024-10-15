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

    public static IfStatementNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException("IfStatementNode");
            return null;
        }
        tryTerminal(tokens, "If", "IfStatementNode");

        //Parsing [<expression>]
        tryTerminal(tokens, "[", "IfStatementNode");
        

        ExpressionNode ifExpression = ExpressionNode.parse(tokens);

        // Parsion [<exp>]
        tryTerminal(tokens, "]", "IfStatementNode");

        //Parsing {<body>}
        tryTerminal(tokens, "{", "IfStatementNode");
        BodyNode body = BodyNode.parse(tokens);
        tryTerminal(tokens, "}", "IfStatementNode");

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

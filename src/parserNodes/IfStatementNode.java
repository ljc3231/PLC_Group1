package parserNodes;

import exceptionFiles.*;
import provided.*;
import helpers.*;
import java.util.ArrayList;

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
        while (tokens.get(0).getToken().equals("ElseIf")){
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
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

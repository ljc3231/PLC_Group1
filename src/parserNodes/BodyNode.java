package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BodyNode implements JottTree {
    boolean hasBodyStatement;
    ArrayList<BodyStatementNode> bodyStatementList;
    ReturnStatementNode returnStatement;
    public static BodyNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if(tokens.isEmpty()){
            throw new EndOfFileException("Body");
        }
        boolean bodyStmt = true;
        ArrayList<BodyStatementNode> bsList = new ArrayList<>();
        ReturnStatementNode rs = null;
        while(tokens.get(0).getTokenType() != TokenType.R_BRACE && !tokens.get(0).getToken().equals("Return")) {
            bsList.add(BodyStatementNode.parse(tokens));
        }

        if(bsList.isEmpty()) {
            bodyStmt = false;
        }
        rs = ReturnStatementNode.parse(tokens);

        return new BodyNode(bodyStmt, bsList, rs);

    }
    public BodyNode(boolean hasBodyStatement, ArrayList<BodyStatementNode> bodyStatementList, ReturnStatementNode returnStatementNode) {
        this.hasBodyStatement = hasBodyStatement;
        this.bodyStatementList = bodyStatementList;
        returnStatement = returnStatementNode;
    }
    @Override
    public String convertToJott() {
        //< body > -> < body_stmt >⋆ < return_stmt >
        String jott = "";
        if (hasBodyStatement) {
            for(BodyStatementNode b : bodyStatementList) {
                jott += b.convertToJott();
            }
            jott += " " + returnStatement.convertToJott();
            return jott;
        }
        return returnStatement.convertToJott();
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

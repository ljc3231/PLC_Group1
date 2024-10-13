package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class BodyNode implements JottTree {
    boolean hasBodyStatement;
    BodyStatementNode bodyStatement;
    ReturnStatementNode returnStatement;
    public static BodyNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if(tokens.isEmpty()){
            throw new EndOfFileException("Body");
        }
        boolean bodyStmt = true;
        BodyStatementNode bs = null;
        ReturnStatementNode rs = null;
        try {
            bs = BodyStatementNode.parse(tokens);
        } catch (JottException e) {
            // if error was thrown from BodyStatementNode, means there is no body statement
            if (e.getSource().equals("BodyStatementNode.java")){
                bodyStmt = false;
            }
            // else, pass the error upwards
            else {
                throw e;
            }
        }
        rs = ReturnStatementNode.parse(tokens);

        return new BodyNode(bodyStmt, bs, rs);

    }
    public BodyNode(boolean hasBodyStatement, BodyStatementNode bodyStatementNode, ReturnStatementNode returnStatementNode) {
        this.hasBodyStatement = hasBodyStatement;
        bodyStatement = bodyStatementNode;
        returnStatement = returnStatementNode;
    }
    @Override
    public String convertToJott() {
        return hasBodyStatement ? bodyStatement.convertToJott() + " " + returnStatement.convertToJott() : returnStatement.convertToJott();
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

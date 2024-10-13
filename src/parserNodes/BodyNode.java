package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class BodyNode implements JottTree {
    boolean hasBodyStatement;
    JottTree bodyStatement;
    JottTree returnStatement;
    public static JottTree parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        if(tokens.isEmpty()){
            throw new EndOfFileException("Body");
        }
        boolean bodyStmt = true;
        JottTree bs = null;
        JottTree rs = null;
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
    public BodyNode(boolean hasBodyStatement, JottTree bodyStatementNode, JottTree returnStatementNode) {
        this.hasBodyStatement = hasBodyStatement;
        bodyStatement = bodyStatementNode;
        returnStatement = returnStatementNode;
    }
    @Override
    public String convertToJott() {
        return hasBodyStatement ? bodyStatement.convertToJott() + returnStatement.convertToJott() : returnStatement.convertToJott();
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

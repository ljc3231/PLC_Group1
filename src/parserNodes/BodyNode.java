package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbolTable.*;

public class BodyNode implements JottTree {
    private final boolean hasBodyStatement;
    private final ArrayList<BodyStatementNode> bodyStatementList;
    private final ReturnStatementNode returnStatement;
    private final boolean needsReturn;
    private final boolean validReturn;
    private static final String FILENAME = "BodyNode";

    public static BodyNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        boolean needsRet = !SymbolTable.getReturnType().toLowerCase().equals("void");
        boolean validRet = false;

        if(tokens.isEmpty()){
            throw new EndOfFileException("Body");
        }
        int lineNum = tokens.get(0).getLineNum();
        boolean bodyStmt = true;
        ArrayList<BodyStatementNode> bsList = new ArrayList<>();
        ReturnStatementNode rs;
        while(tokens.get(0).getTokenType() != TokenType.R_BRACE && !tokens.get(0).getToken().equals("Return")) {
            BodyStatementNode bsNode = BodyStatementNode.parse(tokens);
            bsList.add(bsNode);
            if(tokens.isEmpty()){
                throw new EndOfFileException("Body");
            }

            if (bsNode.validReturn()) {
                validRet = true;
            }
        }

        if(bsList.isEmpty()) {
            bodyStmt = false;
        }
        rs = ReturnStatementNode.parse(tokens);
        if (rs.exists()) {
            validRet = true;
        }

        if (needsRet && !validRet) {
            throw new JottException(false, FILENAME, "Body requires a valid return path", lineNum);
        }

        return new BodyNode(bodyStmt, bsList, rs, needsRet, validRet);
    }

    public BodyNode(boolean hBS, ArrayList<BodyStatementNode> bSL, ReturnStatementNode rSN, boolean nR, boolean vR) {
        this.hasBodyStatement = hBS;
        this.bodyStatementList = bSL;
        this.       returnStatement = rSN;
        this.needsReturn = nR;
        this.validReturn = vR;
    }

    public boolean validReturn() {
        return validReturn;
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
        if(hasBodyStatement) {
            for(BodyStatementNode b : bodyStatementList) {
                if (!b.validateTree()) {
                    return false;
                }
            }
        }
        return returnStatement.validateTree();
    }

    @Override
    public String execute() throws JottException {
        //< body > -> < body_stmt >⋆ < return_stmt >
        if(hasBodyStatement) {
            for(BodyStatementNode b : bodyStatementList) {
                b.execute();
            }
        }
        returnStatement.execute();
        return null;
    }
}

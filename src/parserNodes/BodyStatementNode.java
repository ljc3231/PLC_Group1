package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public interface BodyStatementNode extends JottTree {
    public static final String FILENAME = "BodyStatementNode";

    //< body_stmt > -> < if_stmt > | < while_loop > | < asmt > | < func_call >;
    static BodyStatementNode parse(ArrayList<Token> tokens) throws JottException, EndOfFileException {
        if(tokens.isEmpty()) {
            throw new EndOfFileException("Body statement");
        }
        Token token = tokens.get(0);

        // If Statement: < if_stmt > -> If [ < expr >]{ < body >} < elseif_lst >⋆ < else >
        if(token.getToken().equals("If")){
            return IfStatementNode.parse(tokens);
        }
        // While Loop: < while_loop > -> While [ < expr >]{ < body >}
        if(token.getToken().equals("While")) {
            return WhileLoopNode.parse(tokens);
        }
        // Assignment: < asmt > -> <id >= < expr >;
        if(token.getTokenType() == TokenType.ID_KEYWORD && !token.getToken().equals("Return")) {
            AssignmentNode a = AssignmentNode.parse(tokens);
        }
        //Function Call: < func_call > -> :: < id >[ < params >]
        if(token.getTokenType() == TokenType.FC_HEADER) {
            FuncCallNode f = FuncCallNode.parse(tokens);
            if(tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
                tokens.remove(0);
                return f;
            }
        }
        throw new JottException(FILENAME, "implementation error", token.getLineNum());
    }
}

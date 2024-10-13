package parserNodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public interface BodyStatementNode extends JottTree {

    //< body_stmt > -> < if_stmt > | < while_loop > | < asmt > | < func_call >;
    static JottTree parse(ArrayList<Token> tokens) {
        if(tokens.isEmpty()) {
            System.err.println("expected Body Statement, instead got end of file");
            return null;
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
        if(token.getTokenType() == TokenType.ID_KEYWORD) {
            return AssignmentNode.parse(tokens);
        }
        //Function Call: < func_call > -> :: < id >[ < params >]
        if(token.getTokenType() == TokenType.FC_HEADER) {
            return FuncCallNode.parse(tokens);
        }
        System.err.println("implementation error");
        return null;
    }
}

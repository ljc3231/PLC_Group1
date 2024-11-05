package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import helpers.*;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class VarDecNode implements JottTree, ParseTerminal {
    public static final String FILENAME = "VarDecNode";
    TypeNode typeNode;
    IdNode idNode;
    public static VarDecNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        // < var_dec > -> < type > < id >;
        if(tokens.isEmpty()){
            throw new EndOfFileException("Variable Declaration");
        }
        try {
            TypeNode tn = TypeNode.parse(tokens);
            IdNode id = IdNode.parse(tokens);
            ParseTerminal.parseTerminal(tokens, ";", FILENAME);
            return new VarDecNode(tn, id);
        }
        catch (JottException e) {
            if(e.getSource().equals(TypeNode.FILENAME)) {
                throw new JottException(FILENAME, "No variable declaration found", tokens.get(0).getLineNum());
            }
            throw e;
        }
    }

    public VarDecNode(TypeNode tn, IdNode id) {
        typeNode = tn;
        idNode = id;
    }
    @Override
    public String convertToJott() {
        // < var_dec > -> < type > < id >;
        return typeNode.convertToJott() + " " + idNode.convertToJott() + ";";
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

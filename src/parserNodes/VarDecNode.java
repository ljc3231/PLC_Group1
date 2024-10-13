package parserNodes;

import exceptionFiles.EndOfFileException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    TypeNode typeNode;
    IdNode idNode;
    public static VarDecNode parse(ArrayList<Token> tokens) throws EndOfFileException {
        // < var_dec > -> < type > < id >;
        if(tokens.isEmpty()){
            throw new EndOfFileException("Variable Declaration");
        }
        TypeNode tn = TypeNode.parse(tokens);
        IdNode id = IdNode.parse(tokens);
        return new VarDecNode(tn, id);
    }

    public VarDecNode(TypeNode tn, IdNode id) {
        typeNode = tn;
        idNode = id;
    }
    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott();
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

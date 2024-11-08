package parserNodes;

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import helpers.*;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import symbolTable.SymbolTable;

public class VarDecNode implements JottTree, ParseTerminal {
    public static final String FILENAME = "VarDecNode";
    TypeNode typeNode;
    IdNode idNode;
    public static VarDecNode parse(ArrayList<Token> tokens) throws EndOfFileException, JottException {
        // < var_dec > -> < type > < id >;
        if(tokens.isEmpty()){
            throw new EndOfFileException("Variable Declaration");
        }
        TypeNode tn = TypeNode.parse(tokens);
        IdNode id = IdNode.parse(tokens);
        ParseTerminal.parseTerminal(tokens, ";", FILENAME);
        try {
            SymbolTable.addVariable(id.convertToJott(), tn.convertToJott());
        }
        catch(Exception e) {
            //TODO
            //throw jott expression after its made
        }
        return new VarDecNode(tn, id);
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
        return typeNode.validateTree() && idNode.validateTree();
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

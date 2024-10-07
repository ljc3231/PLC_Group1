package parserNodes;
import java.util.ArrayList;
import provided.*;

public class ProgramNode implements JottTree{
    private final ArrayList<FunctionDefNode> functions;

    public ProgramNode(ArrayList<FunctionDefNode> f) {
        this.functions = f;
    }

    public static ProgramNode parse(ArrayList<Token> tokens) {
        ArrayList<FunctionDefNode> functions = new ArrayList<>();

        while(!tokens.isEmpty()) {
            functions.add(FunctionDefNode.parse(tokens));
        }

        return new ProgramNode(functions);
    }

    @Override
    public String convertToJott() {
        String s = "";
        for (FunctionDefNode f : this.functions) {
            s += f.convertToJott() + "\n";
        }
        return s;
    }

    @Override
    public boolean validateTree() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void execute() {
        for (FunctionDefNode f : this.functions) {
            f.execute();
        }
    }
}

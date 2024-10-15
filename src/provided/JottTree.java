package provided;
import exceptionFiles.*;
import java.util.ArrayList;

/**
 * Interface for all Jott parse tree nodes
 *
 * @author Scott C Johnson
 */
public interface JottTree {

    public static void tryTerminal(ArrayList<Token> tokens, String s, String fName) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException(s);
        }
        String t = tokens.get(1).getToken();
        if (!t.equals(s)) {
            throw new JottException(fName, "Expected \"" + s + "\", instead recieved \"" + t + "\"");
        }
        tokens.remove(0);
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott();

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree();
	
	/**
	 * This will execute the Jott code represented by this JottTree node.
	 */
	public void execute();
}

package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import exceptionFiles.EndOfFileException;
import exceptionFiles.JottException;
import java.util.ArrayList;
import parserNodes.ProgramNode;

public class JottParser {

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        try {
            JottTree pn = ProgramNode.parse(tokens);
            if(!pn.validateTree()) {
                return null;
            }
            return pn;
        } catch (JottException | EndOfFileException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}


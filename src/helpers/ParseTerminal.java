package helpers;

import exceptionFiles.*;
import java.util.ArrayList;
import provided.*;

public interface ParseTerminal {
    public static void parseTerminal(ArrayList<Token> tokens, String s, String fName) throws EndOfFileException, JottException {
        if (tokens.isEmpty()) {
            throw new EndOfFileException(s);
        }
        String t = tokens.get(0).getToken();
        if (!t.equals(s)) {
            throw new JottException(fName, "Expected \"" + s + "\", instead recieved \"" + t + "\"", tokens.get(0).getLineNum());
        }
        tokens.remove(0);
    }
}

package JottMain;

import provided.*;

import java.util.ArrayList;

public class Jott {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Jott requires 1 argument, the file name to run");
        }
        else {
            ArrayList<Token> tokens = JottTokenizer.tokenize(args[0]);
            JottTree jottTree = JottParser.parse(tokens);
        }
    }
}

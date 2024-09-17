package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.*;
import java.util.ArrayList;

import provided.Token;
import provided.TokenType;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){

		try {
			ArrayList<Token> tokens = new ArrayList<>();
			//set up buffered reader to read in character by character
			BufferedReader br = new BufferedReader(new FileReader(filename));

			//Track current line num
			int lineNumber = 1;

			while(true){
				char nextChar;
				int charVal = br.read();	// br.read() reads in as int
				if (charVal == -1){ 		// check for end of stream
					break;
				}
				else{
					nextChar = (char)charVal;
				}

				//******IF STATEMENTS BELOW*****
				if (nextChar == '#'){			// check for comment
					while (nextChar != 10){		//throw out everything in comment
						charVal = br.read();
						if(charVal == -1) {		// in the case we hit end of file
							break;		// we can just break here, and the next read will break us out of main loop
						}
						nextChar = (char)charVal;
					}
				}

				//Start Liam
				//Concat nextChar w/ "" to make into string (must be string for Token Obj)
				else if (nextChar == ',') {
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.COMMA));
				}
				else if (nextChar == '[') {
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.R_BRACKET));
				}
				else if (nextChar == ']') {
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.L_BRACKET));
				}
				else if (nextChar == '{') {
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.R_BRACE));
				}
				else if (nextChar == '}') {
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.L_BRACE));
				}
				// END LIAM
        
        		// START RUSHIL
				else if (nextChar == '=') {
					br.mark(1);
					int nextCharVal = br.read();
					char lookAhead = (char) nextCharVal;
					if (nextCharVal == '=') {
						tokens.add(new Token(nextChar + lookAhead + "", filename, lineNumber, TokenType.REL_OP));
					} else {
						br.reset();
						tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.ASSIGN));
					}
				}
				else if (nextChar == '<' || nextChar == '>') {
					br.mark(1);
					int nextCharVal = br.read();
					char lookAhead = (char) nextCharVal;
					if (nextCharVal == '=') {
						tokens.add(new Token(nextChar + lookAhead + "", filename, lineNumber, TokenType.REL_OP));
					} else {
						br.reset();
						tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.REL_OP));
					}
				}
				else if (nextChar == '/' || nextChar == '+' || nextChar == '-' || nextChar == '*') {
          			tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.MATH_OP));
				}
				else if (nextChar == ';'){
					tokens.add(new Token(nextChar + "", filename, lineNumber, TokenType.SEMICOLON));
				}
        		// END RUSHIL

                // START ALEX
				else if (nextChar == '.') {
					int nextCharVal = br.read();
                    String token = ".";
                    if (nextCharVal == -1) {                // EOF Error
                        String errorMessage = "Expected digit, instead reached end of file";
                        throwErr(true, errorMessage, filename, lineNumber);
                        break;
                    }
                    char lookAhead = (char) nextCharVal;
                    if (!isDigit(lookAhead)) {              // Error Not A Digit
                        String errorMessage = "Expected digit, instead got \'" + lookAhead + "\'";
                        throwErr(true, errorMessage, filename, lineNumber);
                        break;
                    }

                    token += nextCharVal;           // Add the char to the token
                    token = loopDigit(br, token);       // Add any digits
                    tokens.add(new Token(token, filename, lineNumber, TokenType.NUMBER));
                }
				// IF DIGIT
				else if (isDigit(nextChar)){
                    String token = nextChar + "";
                    token = loopDigit(br, token);       // Add any digits
                    
                    br.mark(1);
                    char lookAhead = (char) nextCharVal;
                    if (lookAhead == '.') {         // Once a non digit is met, check if it's a '.'
                        token += '.';               // If it is, add it to the token
                        token = loopDigit(br, token);   // Then add any additional digits 
                        tokens.add(new Token(token, filename, lineNumber, TokenType.NUMBER));
                    }
                    br.reset();
				}
				// IF LETTER
				else if (isLetter(nextChar)) {
                    String token = nextChar + "";
                    while(true) {
                        br.mark(1);
                        char nextCharVal = (char) br.read();
                        if (isLetter(nextCharVal) || isDigit(nextCharVal)) {
                            token += nextCharVal;
                        }
                        else {
                            br.reset();
                            tokens.add(new Token(token, filename, lineNumber, TokenType.ID_KEYWORD));
                            break;
                        }
                    }
				}
                // END ALEX
				else if (nextChar == ':'){

				}
				else if (nextChar == '!') {

				}
				else if (nextChar == '"') {

				}
				
				//If a new line char is hit, increment line number by 1 right before moving to next char (next line)
				if(nextChar == 10){
					lineNumber++;
				}

			}

			return tokens;

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Checks if char is a digit
	 *
	 * @param c char to be checked
	 * @return true if it is digit, else false
	 */
	private static boolean isDigit (char c) {
		return c > 47 && c < 58;		// looking at ascii codes of char
	}

	/**
	 * Checks if char is a letter
	 *
	 * @param c char to be checked
	 * @return true if it is letter, else false
	 */
	private static boolean isLetter (char c) {
		return c > 64 && c < 91 || c > 96 && c < 123;	// same as above, looking at ascii codes
	}

    /**
     * Handles the looping of digits for number tokens
     * 
     * @param token the current token that's being built
     * 
     * @return the finalized token
     */
    private static String loopDigit(BufferedReader br, String token) throws IOException{
        while (true) {
            br.mark(1);
            char lookAhead = (char) br.read();
            if (!isDigit(lookAhead)) {
                br.reset();
                return token;
            }
            token += lookAhead;
        }
    }

	/**
	 * Takes in information on error to throw an error message to the user
	 * 
	 * @param isSyntax true if syntax error, false if semantical error
	 * @param errorMsg the message to be thrown with the error
	 * @param filename the filename of the Jott file
	 * @param lineNum line number of the error 
	 * 
	 * @return none, throws the error itself
	 */

	private static void throwErr(boolean isSyntax, String errorMsg, String filename, int lineNum){
		if(isSyntax){
			System.err("Syntax Error:");
		}
		else{
			System.err("Semantics Error:");
		}
		System.err(errorMsg);
		System.err(filename + ':' + lineNum);
	}
}

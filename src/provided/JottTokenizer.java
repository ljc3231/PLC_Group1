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
				//END LIAM

				else if (nextChar == '=') {

				}
				else if (nextChar == '<' || nextChar == '>') {

				}
				else if (nextChar == '/' || nextChar == '+' || nextChar == '-' || nextChar == '*') {

				}
				else if (nextChar == ';'){

				}
				else if (nextChar == '.') {

				}
				// IF DIGIT
				else if (isDigit(nextChar)){

				}
				// IF LETTER
				else if (isLetter(nextChar)) {

				}
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
	public static boolean isDigit (char c) {
		return c > 47 && c < 58;		// looking at ascii codes of char
	}

	/**
	 * Checks if char is a letter
	 *
	 * @param c char to be checked
	 * @return true if it is letter, else false
	 */
	public static boolean isLetter (char c) {
		return c > 64 && c < 91 || c > 96 && c < 123;	// same as above, looking at ascii codes
	}
}

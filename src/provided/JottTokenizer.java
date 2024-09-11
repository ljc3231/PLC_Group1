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
				else if (nextChar == ',') {

				}
				else if (nextChar == '[') {

				}
				else if (nextChar == ']') {

				}
				else if (nextChar == '{') {

				}
				else if (nextChar == '}') {

				}
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

			}

			return tokens;

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static boolean isDigit (char c) {
		if (c > 47 && c < 58){
			return true;
		}
		return false;
	}

	public static boolean isLetter (char c) {
		if (c > 64 && c < 91 || c > 96 && c < 123) {
			return true;
		}
		return false;
	}
}
